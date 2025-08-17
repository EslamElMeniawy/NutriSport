package elmeniawy.eslam.nutrisport.shared.domain

import kotlinx.serialization.Serializable

/**
 * Customer
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 12:04 PM.
 */
@Serializable
data class Customer(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val city: String? = null,
    val postalCode: Int? = null,
    val address: String? = null,
    val phoneNumber: PhoneNumber? = null,
    val cart: List<CartItem>? = null,
    val isAdmin: Boolean? = null
)

@Serializable
data class PhoneNumber(
    val dialCode: Int? = null,
    val number: String? = null
)