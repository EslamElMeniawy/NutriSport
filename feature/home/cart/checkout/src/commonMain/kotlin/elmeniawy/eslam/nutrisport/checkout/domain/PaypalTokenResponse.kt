package elmeniawy.eslam.nutrisport.checkout.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * PaypalTokenResponse
 *
 * Created by Eslam El-Meniawy on 28-Aug-2025 at 2:35 PM.
 */
@Serializable
data class PaypalTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
)