package elmeniawy.eslam.nutrisport.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.home.domain.DrawerItem
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.IconPrimary
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * DrawerItemCard
 *
 * Created by Eslam El-Meniawy on 12-Aug-2025 at 10:08 AM.
 */

@Composable
@Preview
fun DrawerItemCard(
    drawerItem: DrawerItem,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 99.dp))
            .clickable { onClick?.invoke() }
            .padding(all = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(drawerItem.icon),
            contentDescription = drawerItem.title,
            tint = IconPrimary
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = drawerItem.title,
            color = TextPrimary,
            fontSize = FontSize.EXTRA_REGULAR
        )
    }
}