package elmeniawy.eslam.nutrisport.cart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import elmeniawy.eslam.nutrisport.shared.BorderIdle
import elmeniawy.eslam.nutrisport.shared.Constants.MIN_QUANTITY
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.IconPrimary
import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.RobotoCondensedFont
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.SurfaceLighter
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.TextSecondary
import elmeniawy.eslam.nutrisport.shared.component.QuantityCounter
import elmeniawy.eslam.nutrisport.shared.domain.CartItem
import elmeniawy.eslam.nutrisport.shared.domain.Product
import elmeniawy.eslam.nutrisport.shared.domain.QuantityCounterSize
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * CartItemCard
 *
 * Created by Eslam El-Meniawy on 26-Aug-2025 at 10:58 AM.
 */

@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    product: Product,
    cartItem: CartItem,
    onMinusClick: ((Int) -> Unit)? = null,
    onPlusClick: ((Int) -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(size = 12.dp))
            .background(SurfaceLighter)
    ) {
        AsyncImage(
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(size = 12.dp))
                .border(
                    width = 1.dp,
                    color = BorderIdle,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(product.thumbnail)
                .crossfade(enable = true)
                .build(),
            contentDescription = "Product thumbnail image",
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = product.title ?: "",
                    fontFamily = RobotoCondensedFont(),
                    fontSize = FontSize.MEDIUM,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(size = 6.dp))
                        .background(Surface)
                        .border(
                            width = 1.dp,
                            color = BorderIdle,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                        .clickable { onDeleteClick?.invoke() }
                        .padding(all = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(Resources.Icon.Delete),
                        contentDescription = "Delete icon",
                        tint = IconPrimary
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$${product.price ?: 0.0}",
                    fontSize = FontSize.EXTRA_REGULAR,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )

                QuantityCounter(
                    size = QuantityCounterSize.Small,
                    value = cartItem.quantity ?: MIN_QUANTITY,
                    onMinusClick = onMinusClick,
                    onPlusClick = onPlusClick
                )
            }
        }
    }
}

@Preview
@Composable
fun CartItemCardPreview() {
    val product = Product(
        title = "Optimum Nutrition Gold Standard 100% Whey Protein",
        price = 59.99
    )

    val cartItem = CartItem(
        quantity = 2
    )

    CartItemCard(
        product = product,
        cartItem = cartItem
    )
}