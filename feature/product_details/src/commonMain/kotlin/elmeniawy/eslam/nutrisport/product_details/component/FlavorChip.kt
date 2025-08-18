package elmeniawy.eslam.nutrisport.product_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.shared.BorderIdle
import elmeniawy.eslam.nutrisport.shared.BorderSecondary
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.TextSecondary
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * FlavorChip
 *
 * Created by Eslam El-Meniawy on 18-Aug-2025 at 11:11 AM.
 */

@Composable
@Preview
fun FlavorChip(
    flavor: String,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 12.dp))
            .clickable { onClick?.invoke() }
            .background(Surface)
            .border(
                width = 1.dp,
                color = if (isSelected) BorderSecondary else BorderIdle,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = flavor,
            fontSize = FontSize.SMALL,
            color = if (isSelected) TextSecondary else TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}