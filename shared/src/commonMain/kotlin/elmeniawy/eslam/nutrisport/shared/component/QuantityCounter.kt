package elmeniawy.eslam.nutrisport.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.shared.Constants.MAX_QUANTITY
import elmeniawy.eslam.nutrisport.shared.Constants.MIN_QUANTITY
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.IconPrimary
import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.SurfaceBrand
import elmeniawy.eslam.nutrisport.shared.SurfaceLighter
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.domain.QuantityCounterSize
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * QuantityCounter
 *
 * Created by Eslam El-Meniawy on 18-Aug-2025 at 11:26 AM.
 */

@Composable
fun QuantityCounter(
    modifier: Modifier = Modifier,
    size: QuantityCounterSize,
    value: Int = MIN_QUANTITY,
    onMinusClick: ((Int) -> Unit)? = null,
    onPlusClick: ((Int) -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(size.spacing)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 6.dp))
                .background(SurfaceBrand)
                .clickable { if (value > MIN_QUANTITY) onMinusClick?.invoke(value - 1) }
                .padding(size.padding),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(Resources.Icon.Minus),
                contentDescription = "Minus icon",
                tint = IconPrimary
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 6.dp))
                .background(SurfaceLighter)
                .padding(size.padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+$value",
                fontSize = FontSize.SMALL,
                lineHeight = FontSize.SMALL * 1,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 6.dp))
                .background(SurfaceBrand)
                .clickable { if (value < MAX_QUANTITY) onPlusClick?.invoke(value + 1) }
                .padding(size.padding),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                painter = painterResource(Resources.Icon.Plus),
                contentDescription = "Plus icon",
                tint = IconPrimary
            )
        }
    }
}

@Preview
@Composable
private fun QuantityCounterPreview() {
    QuantityCounter(
        modifier = Modifier.background(color = Surface),
        size = QuantityCounterSize.Small
    )
}