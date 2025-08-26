package elmeniawy.eslam.nutrisport.cart

import ContentWithMessageBar
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.cart.component.CartItemCard
import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.SurfaceBrand
import elmeniawy.eslam.nutrisport.shared.SurfaceError
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.TextWhite
import elmeniawy.eslam.nutrisport.shared.component.InfoCard
import elmeniawy.eslam.nutrisport.shared.component.LoadingCard
import elmeniawy.eslam.nutrisport.shared.util.DisplayResult
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

/**
 * CartScreen
 *
 * Created by Eslam El-Meniawy on 26-Aug-2025 at 11:21 AM.
 */

@Composable
fun CartScreen() {
    val messageBarState = rememberMessageBarState()
    val viewModel = koinViewModel<CartViewModel>()
    val cartItemsWithProducts by viewModel.cartItemsWithProducts.collectAsState(RequestState.Loading)

    ContentWithMessageBar(
        contentBackgroundColor = Surface,
        messageBarState = messageBarState,
        errorMaxLines = 2,
        errorContainerColor = SurfaceError,
        errorContentColor = TextWhite,
        successContainerColor = SurfaceBrand,
        successContentColor = TextPrimary
    ) {
        cartItemsWithProducts.DisplayResult(
            onLoading = { LoadingCard(modifier = Modifier.fillMaxSize()) },
            onSuccess = { data ->
                if (!data.isNullOrEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = data,
                            key = { it.first.id }
                        ) { pair ->
                            CartItemCard(
                                cartItem = pair.first,
                                product = pair.second,
                                onMinusClick = { quantity ->
                                    viewModel.updateCartItemQuantity(
                                        id = pair.first.id,
                                        quantity = quantity,
                                        onSuccess = {},
                                        onError = { messageBarState.addError(it) }
                                    )
                                },
                                onPlusClick = { quantity ->
                                    viewModel.updateCartItemQuantity(
                                        id = pair.first.id,
                                        quantity = quantity,
                                        onSuccess = {},
                                        onError = { messageBarState.addError(it) }
                                    )
                                },
                                onDeleteClick = {
                                    viewModel.deleteCartItem(
                                        id = pair.first.id,
                                        onSuccess = {},
                                        onError = { messageBarState.addError(it) }
                                    )
                                }
                            )
                        }
                    }
                } else {
                    InfoCard(
                        image = Resources.Image.ShoppingCart,
                        title = "Empty Cart",
                        subtitle = "Check some of our products."
                    )
                }
            },
            onError = { message ->
                InfoCard(
                    image = Resources.Image.Cat,
                    title = "Oops!",
                    subtitle = message
                )
            },
            transitionSpec = fadeIn() togetherWith fadeOut()
        )
    }
}