package elmeniawy.eslam.nutrisport.shared.domain

import elmeniawy.eslam.nutrisport.shared.Resources
import org.jetbrains.compose.resources.DrawableResource

/**
 * Country
 *
 * Created by Eslam El-Meniawy on 13-Aug-2025 at 10:15 AM.
 */
enum class Country(
    val dialCode: Int,
    val code: String,
    val flag: DrawableResource
) {
    Egypt(
        dialCode = 20,
        code = "EG",
        flag = Resources.Flag.Egypt
    ),
    Serbia(
        dialCode = 381,
        code = "RS",
        flag = Resources.Flag.Serbia
    ),
    India(
        dialCode = 91,
        code = "IN",
        flag = Resources.Flag.India
    ),
    Usa(
        dialCode = 1,
        code = "US",
        flag = Resources.Flag.Usa
    )
}