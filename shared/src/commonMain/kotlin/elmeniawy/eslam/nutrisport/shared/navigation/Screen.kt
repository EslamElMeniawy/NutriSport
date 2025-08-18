package elmeniawy.eslam.nutrisport.shared.navigation

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

    @Serializable
    data object ProductsOverview : Screen()

    @Serializable
    data class ProductDetails(val id: String? = null) : Screen()

    @Serializable
    data object Cart : Screen()

    @Serializable
    data object Categories : Screen()

    @Serializable
    data object Profile : Screen()

    @Serializable
    data object AdminPanel : Screen()

    @Serializable
    data class ManageProduct(val id: String? = null) : Screen()
}