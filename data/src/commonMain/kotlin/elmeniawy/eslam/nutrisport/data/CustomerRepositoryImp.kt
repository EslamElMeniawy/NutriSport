package elmeniawy.eslam.nutrisport.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.shared.domain.CartItem
import elmeniawy.eslam.nutrisport.shared.domain.Customer
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * CustomerRepositoryImp
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 12:59 PM.
 */
class CustomerRepositoryImp : CustomerRepository {
    override fun getCurrentUserId(): String? = Firebase.auth.currentUser?.uid

    override suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: (() -> Unit)?,
        onError: ((String) -> Unit)?
    ) {
        try {
            if (user != null) {
                val customerCollection = Firebase.firestore.collection("customer")
                val customerExists = customerCollection.document(user.uid).get().exists

                if (!customerExists) {
                    val customer = Customer(
                        id = user.uid,
                        firstName = user.displayName?.split(" ")?.firstOrNull(),
                        lastName = user.displayName?.split(" ")?.lastOrNull(),
                        email = user.email
                    )

                    customerCollection.document(user.uid).set(customer)

                    customerCollection.document(user.uid)
                        .collection("privateData")
                        .document("role")
                        .set(mapOf("isAdmin" to false))
                }

                onSuccess?.invoke()
            } else {
                onError?.invoke("User is not available.")
            }
        } catch (e: Exception) {
            onError?.invoke("Error while creating a Customer: ${e.message}")
        }
    }

    override fun readCustomerFlow(): Flow<RequestState<Customer>> = channelFlow {
        try {
            val userId = getCurrentUserId()

            if (userId != null) {
                val database = Firebase.firestore

                database.collection(collectionPath = "customer")
                    .document(userId).snapshots.collectLatest { document ->
                        if (document.exists) {
                            val privateDataDocument =
                                database.collection(collectionPath = "customer")
                                    .document(userId)
                                    .collection(collectionPath = "privateData")
                                    .document("role")
                                    .get()

                            val customer = Customer(
                                id = document.id,
                                firstName = document.get(field = "firstName"),
                                lastName = document.get(field = "lastName"),
                                email = document.get(field = "email"),
                                city = document.get(field = "city"),
                                postalCode = document.get(field = "postalCode"),
                                address = document.get(field = "address"),
                                phoneNumber = document.get(field = "phoneNumber"),
                                cart = document.get(field = "cart"),
                                isAdmin = privateDataDocument.get(field = "isAdmin")
                            )

                            send(RequestState.Success(data = customer))
                        } else {
                            send(RequestState.Error("Queried customer document does not exist."))
                        }
                    }
            } else {
                send(RequestState.Error("User is not available."))
            }
        } catch (e: Exception) {
            send(RequestState.Error("Error while reading a Customer: ${e.message}"))
        }
    }

    override suspend fun updateCustomer(
        customer: Customer,
        onSuccess: (() -> Unit)?,
        onError: ((String) -> Unit)?
    ) {
        try {
            val userId = getCurrentUserId()

            if (userId != null) {
                val database = Firebase.firestore
                val customerCollection = database.collection(collectionPath = "customer")
                val existingCustomer = customerCollection.document(customer.id ?: "").get()

                if (existingCustomer.exists) {
                    customerCollection.document(customer.id ?: "").update(
                        "firstName" to customer.firstName,
                        "lastName" to customer.lastName,
                        "city" to customer.city,
                        "postalCode" to customer.postalCode,
                        "address" to customer.address,
                        "phoneNumber" to customer.phoneNumber
                    )

                    onSuccess?.invoke()
                } else {
                    onError?.invoke("Customer is not found.")
                }
            } else {
                onError?.invoke("User is not available.")
            }
        } catch (e: Exception) {
            onError?.invoke("Error while updating a Customer: ${e.message}")
        }
    }

    override suspend fun addItemToCart(
        cartItem: CartItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val currentUserId = getCurrentUserId()

            if (currentUserId != null) {
                val database = Firebase.firestore
                val customerCollection = database.collection(collectionPath = "customer")

                val existingCustomer = customerCollection
                    .document(currentUserId)
                    .get()

                if (existingCustomer.exists) {
                    val existingCart = existingCustomer.get<List<CartItem>?>("cart") ?: listOf()
                    val updatedCart = existingCart + (cartItem)

                    customerCollection.document(currentUserId)
                        .set(
                            data = mapOf("cart" to updatedCart),
                            merge = true
                        )

                    onSuccess()
                } else {
                    onError("Select customer does not exist.")
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while adding a product to cart: ${e.message}")
        }
    }

    override suspend fun updateCartItemQuantity(
        id: String,
        quantity: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val currentUserId = getCurrentUserId()

            if (currentUserId != null) {
                val database = Firebase.firestore
                val customerCollection = database.collection(collectionPath = "customer")

                val existingCustomer = customerCollection
                    .document(currentUserId)
                    .get()

                if (existingCustomer.exists) {
                    val existingCart = existingCustomer.get<List<CartItem>?>("cart") ?: listOf()

                    val updatedCart = existingCart.map { cartItem ->
                        if (cartItem.id == id) {
                            cartItem.copy(quantity = quantity)
                        } else cartItem
                    }

                    customerCollection.document(currentUserId)
                        .update(data = mapOf("cart" to updatedCart))

                    onSuccess()
                } else {
                    onError("Select customer does not exist.")
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while updating a product to cart: ${e.message}")
        }
    }

    override suspend fun deleteCartItem(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val currentUserId = getCurrentUserId()

            if (currentUserId != null) {
                val database = Firebase.firestore
                val customerCollection = database.collection(collectionPath = "customer")

                val existingCustomer = customerCollection
                    .document(currentUserId)
                    .get()

                if (existingCustomer.exists) {
                    val existingCart = existingCustomer.get<List<CartItem>?>("cart") ?: listOf()
                    val updatedCart = existingCart.filterNot { it.id == id }

                    customerCollection.document(currentUserId)
                        .update(data = mapOf("cart" to updatedCart))

                    onSuccess()
                } else {
                    onError("Select customer does not exist.")
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while deleting a product from cart: ${e.message}")
        }
    }

    override suspend fun deleteAllCartItems(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val currentUserId = getCurrentUserId()

            if (currentUserId != null) {
                val database = Firebase.firestore
                val customerCollection = database.collection(collectionPath = "customer")

                val existingCustomer = customerCollection
                    .document(currentUserId)
                    .get()

                if (existingCustomer.exists) {
                    customerCollection.document(currentUserId)
                        .update(data = mapOf("cart" to emptyList<List<CartItem>>()))

                    onSuccess()
                } else {
                    onError("Select customer does not exist.")
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while deleting all products from cart: ${e.message}")
        }
    }

    override suspend fun signOut(): RequestState<Unit> = try {
        Firebase.auth.signOut()
        RequestState.Success(data = Unit)
    } catch (e: Exception) {
        RequestState.Error("Error while signing out: ${e.message}")
    }
}