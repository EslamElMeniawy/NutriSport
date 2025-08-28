package elmeniawy.eslam.nutrisport.shared.domain

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Order
 *
 * Created by Eslam El-Meniawy on 28-Aug-2025 at 2:39 PM.
 */
@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Order(
    val id: String = Uuid.random().toHexString(),
    val customerId: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val token: String? = null
)