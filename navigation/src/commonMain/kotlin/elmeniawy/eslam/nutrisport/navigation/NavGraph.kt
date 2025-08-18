package elmeniawy.eslam.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import elmeniawy.eslam.nutrisport.admin_panel.AdminPanelScreen
import elmeniawy.eslam.nutrisport.auth.AuthScreen
import elmeniawy.eslam.nutrisport.home.HomeGraphScreen
import elmeniawy.eslam.nutrisport.manage_product.ManageProductScreen
import elmeniawy.eslam.nutrisport.product_details.ProductDetailsScreen
import elmeniawy.eslam.nutrisport.profile.ProfileScreen
import elmeniawy.eslam.nutrisport.shared.navigation.Screen

/**
 * NavGraph
 *
 * Created by Eslam El-Meniawy on 10-Aug-2025 at 3:38 PM.
 */

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Auth) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable<Screen.Auth> {
            AuthScreen(navigateToHome = {
                navController.navigate(Screen.HomeGraph) {
                    popUpTo<Screen.Auth> { inclusive = true }
                }
            })
        }

        composable<Screen.HomeGraph> {
            HomeGraphScreen(
                navigateToAuth = {
                    navController.navigate(Screen.Auth) {
                        popUpTo<Screen.HomeGraph> { inclusive = true }
                    }
                },
                navigateToProfile = {
                    navController.navigate(Screen.Profile)
                },
                navigateToAdminPanel = {
                    navController.navigate(Screen.AdminPanel)
                },
                navigateToDetails = { productId ->
                    navController.navigate(Screen.ProductDetails(id = productId))
                }
            )
        }

        composable<Screen.ProductDetails> {
            ProductDetailsScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.Profile> {
            ProfileScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.AdminPanel> {
            AdminPanelScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToManageProduct = { id ->
                    navController.navigate(Screen.ManageProduct(id = id))
                }
            )
        }

        composable<Screen.ManageProduct> {
            val id = it.toRoute<Screen.ManageProduct>().id

            ManageProductScreen(
                id = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}