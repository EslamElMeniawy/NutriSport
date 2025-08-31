package elmeniawy.eslam.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import elmeniawy.eslam.nutrisport.admin_panel.AdminPanelScreen
import elmeniawy.eslam.nutrisport.auth.AuthScreen
import elmeniawy.eslam.nutrisport.category_search.CategorySearchScreen
import elmeniawy.eslam.nutrisport.checkout.CheckoutScreen
import elmeniawy.eslam.nutrisport.home.HomeGraphScreen
import elmeniawy.eslam.nutrisport.manage_product.ManageProductScreen
import elmeniawy.eslam.nutrisport.product_details.ProductDetailsScreen
import elmeniawy.eslam.nutrisport.profile.ProfileScreen
import elmeniawy.eslam.nutrisport.shared.domain.ProductCategory
import elmeniawy.eslam.nutrisport.shared.navigation.Screen
import elmeniawy.eslam.nutrisport.shared.util.PreferencesRepository
import lmeniawy.eslam.nutrisport.payment_completed.PaymentCompleted

/**
 * NavGraph
 *
 * Created by Eslam El-Meniawy on 10-Aug-2025 at 3:38 PM.
 */

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Auth) {
    val navController = rememberNavController()

    val preferencesData by PreferencesRepository.readPayPalDataFlow()
        .collectAsState(initial = null)

    LaunchedEffect(preferencesData) {
        preferencesData?.let { paymentCompleted ->
            if (paymentCompleted.token != null) {
                navController.navigate(paymentCompleted)
                PreferencesRepository.reset()
            }
        }
    }

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
                },
                navigateToCategorySearch = { categoryName ->
                    navController.navigate(Screen.CategorySearch(categoryName))
                },
                navigateToCheckout = { totalAmount ->
                    navController.navigate(Screen.Checkout(totalAmount))
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

        composable<Screen.CategorySearch> {
            val category = ProductCategory.valueOf(
                it.toRoute<Screen.CategorySearch>().categoryName
            )

            CategorySearchScreen(
                category = category,
                navigateToDetails = { productId ->
                    navController.navigate(Screen.ProductDetails(id = productId))
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Screen.Checkout> {
            val totalAmount = it.toRoute<Screen.Checkout>().totalAmount

            CheckoutScreen(
                totalAmount = totalAmount.toDoubleOrNull() ?: 0.0,
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToPaymentCompleted = { isSuccess, error ->
                    navController.navigate(Screen.PaymentCompleted(isSuccess, error))
                }
            )
        }

        composable<Screen.PaymentCompleted> {
            PaymentCompleted(
                navigateBack = {
                    navController.navigate(Screen.HomeGraph) {
                        launchSingleTop = true
                        // Clear backstack completely
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}