package elmeniawy.eslam.nutrisport.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.home.domain.BottomBarDestination
import elmeniawy.eslam.nutrisport.shared.IconPrimary
import elmeniawy.eslam.nutrisport.shared.IconSecondary
import elmeniawy.eslam.nutrisport.shared.SurfaceLighter
import elmeniawy.eslam.nutrisport.shared.navigation.Screen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * BottomBar
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 3:27 PM.
 */

@Composable
@Preview
fun BottomBar(
    modifier: Modifier = Modifier,
    selected: Screen = Screen.ProductsOverview,
    onSelect: (BottomBarDestination) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 12.dp))
            .background(SurfaceLighter)
            .padding(vertical = 12.dp, horizontal = 36.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomBarDestination.entries.forEach { destination ->
            val animatedTint by animateColorAsState(
                targetValue = if (selected == destination.screen) IconSecondary else IconPrimary
            )

            IconButton(onClick = { onSelect(destination) }) {
                Icon(
                    painter = painterResource(destination.icon),
                    contentDescription = destination.title,
                    tint = animatedTint
                )
            }
        }
    }
}