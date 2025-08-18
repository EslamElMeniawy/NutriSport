package elmeniawy.eslam.nutrisport.product_details

import ContentWithMessageBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import elmeniawy.eslam.nutrisport.product_details.component.FlavorChip
import elmeniawy.eslam.nutrisport.shared.BebasNeueFont
import elmeniawy.eslam.nutrisport.shared.BorderIdle
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.IconPrimary
import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.RobotoCondensedFont
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.SurfaceBrand
import elmeniawy.eslam.nutrisport.shared.SurfaceError
import elmeniawy.eslam.nutrisport.shared.SurfaceLighter
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.TextSecondary
import elmeniawy.eslam.nutrisport.shared.TextWhite
import elmeniawy.eslam.nutrisport.shared.component.InfoCard
import elmeniawy.eslam.nutrisport.shared.component.LoadingCard
import elmeniawy.eslam.nutrisport.shared.component.PrimaryButton
import elmeniawy.eslam.nutrisport.shared.component.QuantityCounter
import elmeniawy.eslam.nutrisport.shared.domain.ProductCategory
import elmeniawy.eslam.nutrisport.shared.domain.QuantityCounterSize
import elmeniawy.eslam.nutrisport.shared.util.DisplayResult
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

/**
 * ProductDetailsScreen
 *
 * Created by Eslam El-Meniawy on 18-Aug-2025 at 10:49 AM.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    navigateBack: (() -> Unit)? = null
) {
    val viewModel = koinViewModel<ProductDetailsViewModel>()
    val messageBarState = rememberMessageBarState()
    val product by viewModel.product.collectAsState()
    val quantity = viewModel.quantity
    val selectedFlavor = viewModel.selectedFlavor

    Scaffold(
        containerColor = Surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Details",
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.LARGE,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack?.invoke() }) {
                        Icon(
                            painter = painterResource(Resources.Icon.BackArrow),
                            contentDescription = "Back Arrow icon",
                            tint = IconPrimary
                        )
                    }
                },
                actions = {
                    QuantityCounter(
                        size = QuantityCounterSize.Large,
                        value = quantity,
                        onMinusClick = viewModel::updateQuantity,
                        onPlusClick = viewModel::updateQuantity
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Surface,
                    scrolledContainerColor = Surface,
                    navigationIconContentColor = IconPrimary,
                    titleContentColor = TextPrimary,
                    actionIconContentColor = IconPrimary
                )
            )
        }
    ) { padding ->
        product.DisplayResult(
            onLoading = { LoadingCard(modifier = Modifier.fillMaxSize()) },
            onSuccess = { selectedProduct ->
                ContentWithMessageBar(
                    contentBackgroundColor = Surface,
                    modifier = Modifier
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding()
                        ),
                    messageBarState = messageBarState,
                    errorMaxLines = 2,
                    errorContainerColor = SurfaceError,
                    errorContentColor = TextWhite,
                    successContainerColor = SurfaceBrand,
                    successContentColor = TextPrimary
                ) {
                    Column {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 24.dp)
                                .padding(top = 12.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .clip(RoundedCornerShape(size = 12.dp))
                                    .border(
                                        width = 1.dp,
                                        color = BorderIdle,
                                        shape = RoundedCornerShape(size = 12.dp)
                                    ),
                                model = ImageRequest.Builder(LocalPlatformContext.current)
                                    .data(selectedProduct.thumbnail)
                                    .crossfade(enable = true)
                                    .build(),
                                contentDescription = "Product thumbnail image",
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AnimatedContent(
                                    targetState = selectedProduct.category
                                ) { category ->
                                    if (ProductCategory.valueOf(
                                            category ?: ""
                                        ) == ProductCategory.Accessories
                                    ) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    } else {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(14.dp),
                                                painter = painterResource(Resources.Icon.Weight),
                                                contentDescription = "Weight icon"
                                            )

                                            Spacer(modifier = Modifier.width(4.dp))

                                            Text(
                                                text = "${selectedProduct.weight ?: 0}g",
                                                fontSize = FontSize.REGULAR,
                                                color = TextPrimary
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = "$${selectedProduct.price ?: 0.0}",
                                    fontSize = FontSize.MEDIUM,
                                    color = TextSecondary,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = selectedProduct.title ?: "",
                                fontSize = FontSize.EXTRA_MEDIUM,
                                fontWeight = FontWeight.Medium,
                                fontFamily = RobotoCondensedFont(),
                                color = TextPrimary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = selectedProduct.description ?: "",
                                fontSize = FontSize.REGULAR,
                                lineHeight = FontSize.REGULAR * 1.3,
                                color = TextPrimary
                            )
                        }

                        Column(
                            modifier = Modifier
                                .background(
                                    if (selectedProduct.flavors?.isNotEmpty() == true) SurfaceLighter
                                    else Surface
                                )
                                .padding(all = 24.dp)
                        ) {
                            if (selectedProduct.flavors?.isNotEmpty() == true) {
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    selectedProduct.flavors?.forEach { flavor ->
                                        FlavorChip(
                                            flavor = flavor,
                                            isSelected = selectedFlavor == flavor,
                                            onClick = { viewModel.updateFlavor(flavor) }
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))
                            }

                            PrimaryButton(
                                icon = Resources.Icon.ShoppingCart,
                                text = "Add to Cart",
                                enabled = if (selectedProduct.flavors?.isNotEmpty() == true) {
                                    selectedFlavor != null
                                } else {
                                    true
                                },
                                onClick = {
                                    viewModel.addItemToCart(
                                        onSuccess = { messageBarState.addSuccess("Product added to cart.") },
                                        onError = { message -> messageBarState.addError(message) }
                                    )
                                }
                            )
                        }
                    }
                }
            },
            onError = { message ->
                InfoCard(
                    image = Resources.Image.Cat,
                    title = "Oops!",
                    subtitle = message
                )
            }
        )
    }
}