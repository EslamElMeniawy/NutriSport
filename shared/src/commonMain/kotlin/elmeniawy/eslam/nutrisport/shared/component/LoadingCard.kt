package elmeniawy.eslam.nutrisport.shared.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.shared.IconPrimary
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * LoadingCard
 *
 * Created by Eslam El-Meniawy on 13-Aug-2025 at 12:18 PM.
 */

@Composable
@Preview
fun LoadingCard(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = IconPrimary,
            strokeWidth = 2.dp
        )
    }
}