package elmeniawy.eslam.nutrisport.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.data.domain.ProductRepository
import elmeniawy.eslam.nutrisport.shared.Constants.MIN_QUANTITY
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * HomeGraphViewModel
 *
 * Created by Eslam El-Meniawy on 12-Aug-2025 at 11:49 AM.
 */
class HomeGraphViewModel(
    private val _customerRepository: CustomerRepository,
    private val _productRepository: ProductRepository
) : ViewModel() {
    val customer = _customerRepository.readCustomerFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val products = customer
        .flatMapLatest { customerState ->
            if (customerState.isSuccess()) {
                val productIds =
                    customerState.getSuccessData().cart?.map { it.productId ?: "" }?.toSet()

                if (!productIds.isNullOrEmpty()) {
                    _productRepository.readProductsByIdsFlow(productIds.toList())
                } else flowOf(RequestState.Success(emptyList()))
            } else if (customerState.isError()) {
                flowOf(RequestState.Error(customerState.getErrorMessage()))
            } else flowOf(RequestState.Loading)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val cartItemsWithProducts = combine(customer, products) { customerState, productsState ->
        when {
            customerState.isSuccess() && productsState.isSuccess() -> {
                val cart = customerState.getSuccessData().cart
                val products = productsState.getSuccessData()

                val result = cart?.mapNotNull { cartItem ->
                    val product = products.find { it.id == cartItem.productId }
                    product?.let { cartItem to it }
                }

                RequestState.Success(result)
            }

            customerState.isError() -> RequestState.Error(customerState.getErrorMessage())
            productsState.isError() -> RequestState.Error(productsState.getErrorMessage())

            else -> RequestState.Loading
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val totalAmountFlow = cartItemsWithProducts
        .flatMapLatest { data ->
            if (data.isSuccess()) {
                val items = data.getSuccessData()
                val cartItems = items?.map { it.first }
                val products = items?.map { it.second }?.associateBy { it.id }

                val totalPrice = cartItems?.sumOf { cartItem ->
                    val productPrice = products?.get(cartItem.productId)?.price ?: 0.0
                    productPrice * (cartItem.quantity ?: MIN_QUANTITY)
                }

                flowOf(RequestState.Success(totalPrice))
            } else if (data.isError()) flowOf(RequestState.Error(data.getErrorMessage()))
            else flowOf(RequestState.Loading)
        }

    fun signOut(
        onSuccess: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                _customerRepository.signOut()
            }

            if (result.isSuccess()) {
                onSuccess?.invoke()
            } else if (result.isError()) {
                onError?.invoke(result.getErrorMessage())
            }
        }
    }
}