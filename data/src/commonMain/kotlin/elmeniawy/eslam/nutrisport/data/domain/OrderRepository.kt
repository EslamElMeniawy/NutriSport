package elmeniawy.eslam.nutrisport.data.domain

import elmeniawy.eslam.nutrisport.shared.domain.Order

/**
 * OrderRepository
 *
 * Created by Eslam El-Meniawy on 28-Aug-2025 at 2:38 PM.
 */
interface OrderRepository {
    fun getCurrentUserId(): String?

    suspend fun createTheOrder(
        order: Order,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}