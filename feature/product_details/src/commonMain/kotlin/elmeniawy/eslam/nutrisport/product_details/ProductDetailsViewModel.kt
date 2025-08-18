package elmeniawy.eslam.nutrisport.product_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.data.domain.ProductRepository
import elmeniawy.eslam.nutrisport.shared.domain.CartItem
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ProductDetailsViewModel
 *
 * Created by Eslam El-Meniawy on 18-Aug-2025 at 10:51 AM.
 */
class ProductDetailsViewModel(
    private val _productRepository: ProductRepository,
    private val _customerRepository: CustomerRepository,
    private val _savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val product = _productRepository.readProductByIdFlow(
        _savedStateHandle.get<String>("id") ?: ""
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RequestState.Loading
    )

    var quantity by mutableStateOf(1)
        private set

    var selectedFlavor: String? by mutableStateOf(null)
        private set

    fun updateQuantity(value: Int) {
        quantity = value
    }

    fun updateFlavor(value: String) {
        selectedFlavor = value
    }

    fun addItemToCart(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val productId = _savedStateHandle.get<String>("id")

            if (productId != null) {
                _customerRepository.addItemToCart(
                    cartItem = CartItem(
                        productId = productId,
                        flavor = selectedFlavor,
                        quantity = quantity
                    ),
                    onSuccess = onSuccess,
                    onError = onError
                )
            } else {
                onError("Product id is not found.")
            }
        }
    }
}