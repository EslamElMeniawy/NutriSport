package elmeniawy.eslam.nutrisport.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.shared.domain.Customer
import elmeniawy.eslam.nutrisport.shared.util.RequestState

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
                }

                onSuccess?.invoke()
            } else {
                onError?.invoke("User is not available.")
            }
        } catch (e: Exception) {
            onError?.invoke("Error while creating a Customer: ${e.message}")
        }
    }

    override suspend fun signOut(): RequestState<Unit> = try {
        Firebase.auth.signOut()
        RequestState.Success(data = Unit)
    } catch (e: Exception) {
        RequestState.Error("Error while signing out: ${e.message}")
    }
}