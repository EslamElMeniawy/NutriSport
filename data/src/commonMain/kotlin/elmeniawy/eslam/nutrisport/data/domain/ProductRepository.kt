package elmeniawy.eslam.nutrisport.data.domain

import elmeniawy.eslam.nutrisport.shared.domain.Product
import elmeniawy.eslam.nutrisport.shared.domain.ProductCategory
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow

/**
 * ProductRepository
 *
 * Created by Eslam El-Meniawy on 17-Aug-2025 at 2:15 PM.
 */
interface ProductRepository {
    fun getCurrentUserId(): String?

    fun readDiscountedProducts(): Flow<RequestState<List<Product>>>

    fun readNewProducts(): Flow<RequestState<List<Product>>>

    fun readProductByIdFlow(id: String): Flow<RequestState<Product>>

    fun readProductsByIdsFlow(ids: List<String>): Flow<RequestState<List<Product>>>

    fun readProductsByCategoryFlow(category: ProductCategory): Flow<RequestState<List<Product>>>
}