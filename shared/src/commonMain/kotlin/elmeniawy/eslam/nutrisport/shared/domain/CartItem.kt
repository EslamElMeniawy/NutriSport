@file:OptIn(ExperimentalUuidApi::class)

package elmeniawy.eslam.nutrisport.shared.domain

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * CartItem
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 12:12 PM.
 */
@Serializable
data class CartItem(
    val id: String = Uuid.random().toHexString(),
    val productId: String? = null,
    val flavor: String? = null,
    val quantity: Int? = null
)
