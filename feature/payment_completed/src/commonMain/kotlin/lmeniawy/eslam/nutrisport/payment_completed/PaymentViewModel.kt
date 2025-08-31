package lmeniawy.eslam.nutrisport.payment_completed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.data.domain.OrderRepository
import elmeniawy.eslam.nutrisport.data.domain.ProductRepository
import elmeniawy.eslam.nutrisport.shared.Constants.MIN_QUANTITY
import elmeniawy.eslam.nutrisport.shared.domain.CartItem
import elmeniawy.eslam.nutrisport.shared.domain.Order
import elmeniawy.eslam.nutrisport.shared.domain.Product
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * PaymentViewModel
 *
 * Created by Eslam El-Meniawy on 31-Aug-2025 at 10:38 AM.
 */
class PaymentViewModel(
    private val _savedStateHandle: SavedStateHandle,
    private val _customerRepository: CustomerRepository,
    private val _orderRepository: OrderRepository,
    private val _productRepository: ProductRepository,
) : ViewModel() {
    var screenState: RequestState<Unit> by mutableStateOf(RequestState.Loading)

    private val customer = _customerRepository.readCustomerFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val totalAmount = customer.flatMapLatest { customerState ->
        when {
            customerState.isSuccess() -> {
                val cartItems = customerState.getSuccessData().cart
                val productIds = cartItems?.map { it.productId ?: "" }

                if (productIds.isNullOrEmpty()) {
                    flowOf(RequestState.Success(0.0))
                } else {
                    _productRepository.readProductsByIdsFlow(productIds)
                        .map { products ->
                            if (products.isSuccess()) {
                                RequestState.Success(
                                    calculateTotalPrice(
                                        cartItems = cartItems,
                                        products = products.getSuccessData()
                                    )
                                )
                            } else {
                                RequestState.Error(products.getErrorMessage())
                            }
                        }
                }
            }

            customerState.isError() -> flowOf(RequestState.Error(customerState.getErrorMessage()))

            else -> flowOf(RequestState.Loading)
        }
    }

    init {
        viewModelScope.launch {
            totalAmount.collectLatest { amount ->
                if (amount.isSuccess()) {
                    val isSuccess = _savedStateHandle.get<Boolean>("isSuccess")
                    val error = _savedStateHandle.get<String>("error")
                    val token = _savedStateHandle.get<String>("token")

                    if (isSuccess != null) {
                        screenState = RequestState.Success(Unit)

                        if (token != null) {
                            createTheOrder(
                                totalAmount = amount.getSuccessData(),
                                token = token,
                                onError = { message ->
                                    screenState = RequestState.Error(message)
                                }
                            )
                        }
                    } else if (error != null) {
                        screenState = RequestState.Error(error)
                    } else {
                        screenState =
                            RequestState.Error("Unknown error. Contact us at: example@gmail.com")
                    }
                } else if (amount.isError()) {
                    screenState = RequestState.Error(amount.getErrorMessage())
                }
            }
        }
    }

    private fun createTheOrder(
        totalAmount: Double,
        token: String,
        onError: (String) -> Unit,
    ) {
        if (customer.value.isSuccess()) {
            val customerId = customer.value.getSuccessData().id

            viewModelScope.launch(Dispatchers.IO) {
                _orderRepository.createTheOrder(
                    order = Order(
                        customerId = customerId ?: "",
                        items = customer.value.getSuccessData().cart ?: listOf(),
                        totalAmount = totalAmount,
                        token = token
                    ),
                    onSuccess = { println("ORDER SUCCESSFULLY CREATED!") },
                    onError = { message -> onError(message) }
                )
            }
        } else if (customer.value.isError()) onError(customer.value.getErrorMessage())
    }

    fun calculateTotalPrice(cartItems: List<CartItem>, products: List<Product>): Double {
        return cartItems.sumOf { cartItem ->
            val product = products.find { it.id == cartItem.productId }
            product?.price?.times(cartItem.quantity ?: MIN_QUANTITY) ?: 0.0
        }
    }
}