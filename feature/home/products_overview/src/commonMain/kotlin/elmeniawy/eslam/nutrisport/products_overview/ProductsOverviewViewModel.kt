package elmeniawy.eslam.nutrisport.products_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elmeniawy.eslam.nutrisport.data.domain.ProductRepository
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * ProductsOverviewViewModel
 *
 * Created by Eslam El-Meniawy on 17-Aug-2025 at 2:14 PM.
 */
class ProductsOverviewViewModel(
    private val _productRepository: ProductRepository
) : ViewModel() {
    val products = combine(
        _productRepository.readNewProducts(),
        _productRepository.readDiscountedProducts()
    ) { new, discounted ->
        when {
            new.isSuccess() && discounted.isSuccess() -> {
                RequestState.Success(new.getSuccessData() + discounted.getSuccessData())
            }

            new.isError() -> new
            discounted.isError() -> discounted
            else -> RequestState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RequestState.Loading
    )
}