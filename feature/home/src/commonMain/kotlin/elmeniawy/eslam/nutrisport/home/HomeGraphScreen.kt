@file:OptIn(ExperimentalMaterial3Api::class)

package elmeniawy.eslam.nutrisport.home

import ContentWithMessageBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import elmeniawy.eslam.nutrisport.cart.CartScreen
import elmeniawy.eslam.nutrisport.home.component.BottomBar
import elmeniawy.eslam.nutrisport.home.component.CustomDrawer
import elmeniawy.eslam.nutrisport.home.domain.BottomBarDestination
import elmeniawy.eslam.nutrisport.home.domain.CustomDrawerState
import elmeniawy.eslam.nutrisport.home.domain.isOpened
import elmeniawy.eslam.nutrisport.home.domain.opposite
import elmeniawy.eslam.nutrisport.products_overview.ProductsOverviewScreen
import elmeniawy.eslam.nutrisport.shared.Alpha
import elmeniawy.eslam.nutrisport.shared.BebasNeueFont
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.IconPrimary
import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.SurfaceBrand
import elmeniawy.eslam.nutrisport.shared.SurfaceError
import elmeniawy.eslam.nutrisport.shared.SurfaceLighter
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.TextWhite
import elmeniawy.eslam.nutrisport.shared.navigation.Screen
import elmeniawy.eslam.nutrisport.shared.util.getScreenWidth
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

/**
 * HomeGraphScreen
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 2:52 PM.
 */

@Composable
fun HomeGraphScreen(
    navigateToAuth: (() -> Unit)? = null,
    navigateToProfile: (() -> Unit)? = null,
    navigateToAdminPanel: (() -> Unit)? = null,
    navigateToDetails: ((String?) -> Unit)? = null
) {
    val viewModel = koinViewModel<HomeGraphViewModel>()
    val customer by viewModel.customer.collectAsState()
    val messageBarState = rememberMessageBarState()
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

    val screenWidth = remember { getScreenWidth() }
    var drawableState by remember { mutableStateOf(CustomDrawerState.Closed) }
    val offsetValue by remember { derivedStateOf { (screenWidth / 1.5).dp } }

    val animatedOffset by animateDpAsState(
        targetValue = if (drawableState.isOpened()) offsetValue else 0.dp
    )

    val animatedBackground by animateColorAsState(
        targetValue = if (drawableState.isOpened()) SurfaceLighter else Surface
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (drawableState.isOpened()) 0.9f else 1f
    )

    val animatedRadius by animateDpAsState(
        targetValue = if (drawableState.isOpened()) 20.dp else 0.dp
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedBackground)
            .systemBarsPadding()
    ) {
        CustomDrawer(
            customer = customer,
            onProfileClick = navigateToProfile,
            onContactUsClick = {},
            onSignOutClick = {
                viewModel.signOut(
                    onSuccess = { navigateToAuth?.invoke() },
                    onError = { message -> messageBarState.addError(message) }
                )
            },
            onAdminPanelClick = navigateToAdminPanel,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(size = animatedRadius))
                .offset(x = animatedOffset)
                .scale(scale = animatedScale)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(size = animatedRadius),
                    ambientColor = Color.Black.copy(alpha = Alpha.DISABLED),
                    spotColor = Color.Black.copy(alpha = Alpha.DISABLED)
                )
        ) {
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
                            AnimatedContent(targetState = drawableState) { drawableS ->
                                IconButton(onClick = { drawableState = drawableState.opposite() }) {
                                    if (drawableS.isOpened()) {
                                        Icon(
                                            painter = painterResource(Resources.Icon.Close),
                                            contentDescription = "Close icon",
                                            tint = IconPrimary
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(Resources.Icon.Menu),
                                            contentDescription = "Menu icon",
                                            tint = IconPrimary
                                        )
                                    }
                                }
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
                ContentWithMessageBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding()
                        ),
                    messageBarState = messageBarState,
                    errorMaxLines = 2,
                    contentBackgroundColor = Surface,
                    errorContainerColor = SurfaceError,
                    errorContentColor = TextWhite,
                    successContainerColor = SurfaceBrand,
                    successContentColor = TextPrimary
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = Modifier.weight(1f),
                            navController = navController,
                            startDestination = Screen.ProductsOverview
                        ) {
                            composable<Screen.ProductsOverview> {
                                ProductsOverviewScreen(
                                    navigateToDetails = navigateToDetails
                                )
                            }

                            composable<Screen.Cart> {
                                CartScreen()
                            }

                            composable<Screen.Categories> { }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(modifier = Modifier.padding(all = 12.dp)) {
                            BottomBar(
                                customer = customer,
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
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}