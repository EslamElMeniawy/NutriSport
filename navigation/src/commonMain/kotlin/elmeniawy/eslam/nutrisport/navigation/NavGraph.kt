package elmeniawy.eslam.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import elmeniawy.eslam.nutrisport.auth.AuthScreen
import elmeniawy.eslam.nutrisport.home.HomeGraphScreen
import elmeniawy.eslam.nutrisport.profile.ProfileScreen
import elmeniawy.eslam.nutrisport.shared.navigation.Screen
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * NavGraph
 *
 * Created by Eslam El-Meniawy on 10-Aug-2025 at 3:38 PM.
 */

@Composable
@Preview
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
    }
}