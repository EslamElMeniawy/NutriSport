package elmeniawy.eslam.nutrisport.category_search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elmeniawy.eslam.nutrisport.data.domain.ProductRepository
import elmeniawy.eslam.nutrisport.shared.domain.ProductCategory
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

/**
 * CategorySearchViewModel
 *
 * Created by Eslam El-Meniawy on 28-Aug-2025 at 10:26 AM.
 */
class CategorySearchViewModel(
    private val _productRepository: ProductRepository,
    private val _savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _products = _productRepository.readProductsByCategoryFlow(
        category = ProductCategory.valueOf(
            _savedStateHandle.get<String>("categoryName") ?: ProductCategory.Protein.name
        )
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RequestState.Loading
    )

    private var _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(value: String) {
        _searchQuery.value = value
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val filteredProducts = searchQuery
        .debounce(500)
        .flatMapLatest { query ->
            if (query.isBlank()) _products
            else {
                if (_products.value.isSuccess()) {
                    flowOf(
                        RequestState.Success(
                            _products.value.getSuccessData()
                                .filter {
                                    it.title?.lowercase()?.contains(query.lowercase()) == true
                                }
                        )
                    )
                } else _products
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )
}