package elmeniawy.eslam.nutrisport.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.shared.BebasNeueFont
import elmeniawy.eslam.nutrisport.shared.BorderIdle
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.SurfaceLighter
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.domain.ProductCategory
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * CategoryCard
 *
 * Created by Eslam El-Meniawy on 28-Aug-2025 at 10:09 AM.
 */

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: ProductCategory,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceLighter)
            .border(
                width = 1.dp,
                color = BorderIdle,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick?.invoke() }
            .padding(
                vertical = 20.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(category.color)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = category.title,
            fontSize = FontSize.EXTRA_MEDIUM,
            fontFamily = BebasNeueFont(),
            color = TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    CategoryCard(
        category = ProductCategory.Protein,
    )
}