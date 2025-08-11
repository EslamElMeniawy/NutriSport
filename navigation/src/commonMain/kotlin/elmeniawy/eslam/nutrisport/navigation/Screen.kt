package elmeniawy.eslam.nutrisport.navigation

import kotlinx.serialization.Serializable

/**
 * Screen
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 11:23 AM.
 */
@Serializable
sealed class Screen {
    @Serializable
    data object Auth : Screen()

    @Serializable
    data object HomeGraph : Screen()
}