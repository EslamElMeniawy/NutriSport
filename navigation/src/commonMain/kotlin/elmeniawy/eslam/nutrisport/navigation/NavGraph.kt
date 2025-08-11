package elmeniawy.eslam.nutrisport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import elmeniawy.eslam.nutrisport.auth.AuthScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * NavGraph
 *
 * Created by Eslam El-Meniawy on 10-Aug-2025 at 3:38 PM.
 */

@Composable
@Preview
fun SetupNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Auth) {
        composable<Screen.Auth> { AuthScreen() }
    }
}