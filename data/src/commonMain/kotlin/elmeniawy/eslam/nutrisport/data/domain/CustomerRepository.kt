package elmeniawy.eslam.nutrisport.data.domain

import dev.gitlive.firebase.auth.FirebaseUser

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
}