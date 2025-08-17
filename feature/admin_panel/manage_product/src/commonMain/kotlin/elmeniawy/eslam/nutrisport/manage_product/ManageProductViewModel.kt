package elmeniawy.eslam.nutrisport.manage_product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.storage.File
import elmeniawy.eslam.nutrisport.data.domain.AdminRepository
import elmeniawy.eslam.nutrisport.shared.domain.Product
import elmeniawy.eslam.nutrisport.shared.domain.ProductCategory
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * ManageProductViewModel
 *
 * Created by Eslam El-Meniawy on 14-Aug-2025 at 11:06 AM.
 */

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
data class ManageProductState(
    val id: String = Uuid.random().toHexString(),
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val title: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,
    val category: ProductCategory = ProductCategory.Protein,
    val flavors: String? = null,
    val weight: Int? = null,
    val price: Double = 0.0,
    val isNew: Boolean = false,
    val isPopular: Boolean = false,
    val isDiscounted: Boolean = false
)

class ManageProductViewModel(
    private val _adminRepository: AdminRepository,
    private val _savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _productId = _savedStateHandle.get<String>("id") ?: ""

    var screenState by mutableStateOf(ManageProductState())
        private set

    var thumbnailUploaderState: RequestState<Unit> by mutableStateOf(RequestState.Idle)
        private set

    val isFormValid: Boolean
        get() = !screenState.title.isNullOrBlank() &&
                !screenState.description.isNullOrBlank() &&
                !screenState.thumbnail.isNullOrBlank() &&
                screenState.price != 0.0

    init {
        _productId.takeIf { it.isNotEmpty() }?.let { id ->
            viewModelScope.launch {
                val selectedProduct = _adminRepository.readProductById(id)

                if (selectedProduct.isSuccess()) {
                    val product = selectedProduct.getSuccessData()
                    updateId(product.id ?: "")
                    updateCreatedAt(product.createdAt)
                    updateTitle(product.title)
                    updateDescription(product.description)
                    updateThumbnail(product.thumbnail)

                    product.thumbnail?.let {
                        updateThumbnailUploaderState(RequestState.Success(Unit))
                    }

                    updateCategory(ProductCategory.valueOf(product.category ?: ""))
                    updateFlavors(product.flavors?.joinToString(",") ?: "")
                    updateWeight(product.weight)
                    updatePrice(product.price ?: 0.0)
                    updateNew(product.isNew ?: false)
                    updatePopular(product.isPopular ?: false)
                    updateDiscounted(product.isDiscounted ?: false)
                }
            }
        }
    }

    fun updateId(value: String) {
        screenState = screenState.copy(id = value)
    }

    fun updateCreatedAt(value: Long) {
        screenState = screenState.copy(createdAt = value)
    }

    fun updateTitle(value: String?) {
        screenState = screenState.copy(title = value)
    }

    fun updateDescription(value: String?) {
        screenState = screenState.copy(description = value)
    }

    fun updateThumbnail(value: String?) {
        screenState = screenState.copy(thumbnail = value)
    }

    fun updateThumbnailUploaderState(value: RequestState<Unit>) {
        thumbnailUploaderState = value
    }

    fun updateCategory(value: ProductCategory) {
        screenState = screenState.copy(category = value)
    }

    fun updateFlavors(value: String) {
        screenState = screenState.copy(flavors = value)
    }

    fun updateWeight(value: Int?) {
        screenState = screenState.copy(weight = value)
    }

    fun updatePrice(value: Double) {
        screenState = screenState.copy(price = value)
    }

    fun updateNew(value: Boolean) {
        screenState = screenState.copy(isNew = value)
    }

    fun updatePopular(value: Boolean) {
        screenState = screenState.copy(isPopular = value)
    }

    fun updateDiscounted(value: Boolean) {
        screenState = screenState.copy(isDiscounted = value)
    }

    fun createNewProduct(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            _adminRepository.createNewProduct(
                product = Product(
                    id = screenState.id,
                    title = screenState.title,
                    description = screenState.description,
                    thumbnail = screenState.thumbnail,
                    category = screenState.category.name,
                    flavors = screenState.flavors?.split(","),
                    weight = screenState.weight,
                    price = screenState.price,
                    isNew = screenState.isNew,
                    isPopular = screenState.isPopular,
                    isDiscounted = screenState.isDiscounted
                ),
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }

    fun uploadThumbnailToStorage(
        file: File?,
        onSuccess: () -> Unit,
    ) {
        if (file == null) {
            updateThumbnailUploaderState(RequestState.Error("File is null. Error while selecting an image."))
            return
        }

        updateThumbnailUploaderState(RequestState.Loading)

        viewModelScope.launch {
            try {
                val downloadUrl = _adminRepository.uploadImageToStorage(file)

                if (downloadUrl.isNullOrEmpty()) {
                    throw Exception("Failed to retrieve a download URL after the upload.")
                }

                _productId.takeIf { it.isNotEmpty() }?.let { id ->
                    _adminRepository.updateProductThumbnail(
                        productId = id,
                        downloadUrl = downloadUrl,
                        onSuccess = {
                            onSuccess()
                            updateThumbnailUploaderState(RequestState.Success(Unit))
                            updateThumbnail(downloadUrl)
                        },
                        onError = { message ->
                            updateThumbnailUploaderState(RequestState.Error(message))
                        }
                    )
                } ?: run {
                    onSuccess()
                    updateThumbnailUploaderState(RequestState.Success(Unit))
                    updateThumbnail(downloadUrl)
                }
            } catch (e: Exception) {
                updateThumbnailUploaderState(RequestState.Error("Error while uploading: $e"))
            }
        }
    }

    fun updateProduct(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        if (isFormValid) {
            viewModelScope.launch {
                _adminRepository.updateProduct(
                    product = Product(
                        id = screenState.id,
                        createdAt = screenState.createdAt,
                        title = screenState.title,
                        description = screenState.description,
                        thumbnail = screenState.thumbnail,
                        category = screenState.category.name,
                        flavors = screenState.flavors?.split(",")
                            ?.map { it.trim() }
                            ?.filter { it.isNotEmpty() },
                        weight = screenState.weight,
                        price = screenState.price,
                        isNew = screenState.isNew,
                        isPopular = screenState.isPopular,
                        isDiscounted = screenState.isDiscounted
                    ),
                    onSuccess = onSuccess,
                    onError = onError
                )
            }
        } else {
            onError("Please fill in the information.")
        }
    }

    fun deleteThumbnailFromStorage(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        viewModelScope.launch {
            _adminRepository.deleteImageFromStorage(
                downloadUrl = screenState.thumbnail ?: "",
                onSuccess = {
                    _productId.takeIf { it.isNotEmpty() }?.let { id ->
                        viewModelScope.launch {
                            _adminRepository.updateProductThumbnail(
                                productId = id,
                                downloadUrl = "",
                                onSuccess = {
                                    updateThumbnail(value = "")
                                    updateThumbnailUploaderState(RequestState.Idle)
                                    onSuccess()
                                },
                                onError = { message -> onError(message) }
                            )
                        }
                    } ?: run {
                        updateThumbnail(value = "")
                        updateThumbnailUploaderState(RequestState.Idle)
                        onSuccess()
                    }
                },
                onError = onError
            )
        }
    }

    fun deleteProduct(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        _productId.takeIf { it.isNotEmpty() }?.let { id ->
            viewModelScope.launch {
                _adminRepository.deleteProduct(
                    productId = id,
                    onSuccess = {
                        deleteThumbnailFromStorage(
                            onSuccess = {},
                            onError = {}
                        )
                        onSuccess()
                    },
                    onError = { message -> onError(message) }
                )
            }
        }
    }
}