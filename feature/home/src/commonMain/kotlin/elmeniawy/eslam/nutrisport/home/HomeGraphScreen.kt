@file:OptIn(ExperimentalMaterial3Api::class)

package elmeniawy.eslam.nutrisport.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import elmeniawy.eslam.nutrisport.home.component.BottomBar
import elmeniawy.eslam.nutrisport.home.domain.BottomBarDestination
import elmeniawy.eslam.nutrisport.shared.BebasNeueFont
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.IconPrimary
import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.navigation.Screen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * HomeGraphScreen
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 2:52 PM.
 */

@Composable
@Preview
fun HomeGraphScreen() {
    val navController = rememberNavController()
    val currentRouteState = navController.currentBackStackEntryAsState()

    val selectedDestination by remember {
        derivedStateOf {
            val route = currentRouteState.value?.destination?.route

            when {
                route?.contains(BottomBarDestination.ProductsOverview.screen.toString()) == true -> BottomBarDestination.ProductsOverview
                route?.contains(BottomBarDestination.Cart.screen.toString()) == true -> BottomBarDestination.Cart
                route?.contains(BottomBarDestination.Categories.screen.toString()) == true -> BottomBarDestination.Categories
                else -> BottomBarDestination.ProductsOverview
            }
        }
    }

    Scaffold(
        containerColor = Surface,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AnimatedContent(targetState = selectedDestination) { destination ->
                        Text(
                            text = destination.title,
                            fontFamily = BebasNeueFont(),
                            fontSize = FontSize.LARGE,
                            color = TextPrimary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Resources.Icon.Menu),
                            contentDescription = "Menu icon",
                            tint = IconPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Surface,
                    scrolledContainerColor = Surface,
                    navigationIconContentColor = IconPrimary,
                    titleContentColor = TextPrimary,
                    actionIconContentColor = IconPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            )
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = Screen.ProductsOverview
            ) {
                composable<Screen.ProductsOverview> { }
                composable<Screen.Cart> { }
                composable<Screen.Categories> { }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.padding(all = 12.dp)) {
                BottomBar(
                    selected = selectedDestination,
                    onSelect = { destination ->
                        navController.navigate(destination.screen) {
                            launchSingleTop = true

                            popUpTo<Screen.ProductsOverview> {
                                saveState = true
                                inclusive = false
                            }

                            restoreState = true
                        }
                    })
            }
        }
    }
}