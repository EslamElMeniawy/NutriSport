package elmeniawy.eslam.nutrisport.data.domain

import dev.gitlive.firebase.storage.File
import elmeniawy.eslam.nutrisport.shared.domain.Product
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow

/**
 * AdminRepository
 *
 * Created by Eslam El-Meniawy on 14-Aug-2025 at 10:26 AM.
 */
interface AdminRepository {
    fun getCurrentUserId(): String?

    suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )

    suspend fun uploadImageToStorage(file: File): String?

    suspend fun deleteImageFromStorage(
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )

    fun readLastTenProducts(): Flow<RequestState<List<Product>>>

    suspend fun readProductById(id: String): RequestState<Product>

    suspend fun updateProductThumbnail(
        productId: String,
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )

    suspend fun updateProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )

    suspend fun deleteProduct(
        productId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )

    fun searchProductsByTitle(
        searchQuery: String,
    ): Flow<RequestState<List<Product>>>
}