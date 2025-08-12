package elmeniawy.eslam.nutrisport.data.domain

import dev.gitlive.firebase.auth.FirebaseUser
import elmeniawy.eslam.nutrisport.shared.util.RequestState

/**
 * CustomerRepository
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 12:53 PM.
 */
interface CustomerRepository {
    fun getCurrentUserId(): String?

    suspend fun createCustomer(
        user: FirebaseUser? = null,
        onSuccess: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    )

    suspend fun signOut(): RequestState<Unit>
}