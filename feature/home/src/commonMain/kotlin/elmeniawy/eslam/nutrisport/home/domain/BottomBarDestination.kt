package elmeniawy.eslam.nutrisport.home.domain

import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.navigation.Screen
import org.jetbrains.compose.resources.DrawableResource

/**
 * BottomBarDestination
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 3:18 PM.
 */
enum class BottomBarDestination(
    val icon: DrawableResource,
    val title: String,
    val screen: Screen
) {
    ProductsOverview(
        icon = Resources.Icon.Home,
        title = "Nutri Sport",
        screen = Screen.ProductsOverview
    ),
    Cart(
        icon = Resources.Icon.ShoppingCart,
        title = "Cart",
        screen = Screen.Cart
    ),
    Categories(
        icon = Resources.Icon.Categories,
        title = "Categories",
        screen = Screen.Categories
    )
}