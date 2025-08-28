package elmeniawy.eslam.nutrisport.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.data.domain.OrderRepository
import elmeniawy.eslam.nutrisport.shared.domain.Order

/**
 * OrderRepositoryImpl
 *
 * Created by Eslam El-Meniawy on 28-Aug-2025 at 2:40 PM.
 */
class OrderRepositoryImpl(
    private val _customerRepository: CustomerRepository,
) : OrderRepository {
    override fun getCurrentUserId() = Firebase.auth.currentUser?.uid

    override suspend fun createTheOrder(
        order: Order,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            val currentUserId = getCurrentUserId()

            if (currentUserId != null) {
                val database = Firebase.firestore
                val orderCollection = database.collection(collectionPath = "order")
                orderCollection.document(order.id).set(order)

                _customerRepository.deleteAllCartItems(
                    onSuccess = {},
                    onError = {}
                )

                onSuccess()
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while adding a product to cart: ${e.message}")
        }
    }
}