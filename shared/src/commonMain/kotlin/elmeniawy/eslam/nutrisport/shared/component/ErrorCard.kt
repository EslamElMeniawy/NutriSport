package elmeniawy.eslam.nutrisport.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.Surface
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * ErrorCard
 *
 * Created by Eslam El-Meniawy on 13-Aug-2025 at 10:57 AM.
 */

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    message: String,
    fontSize: TextUnit = FontSize.SMALL
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            fontSize = fontSize,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun ErrorCardPreview() {
    ErrorCard(
        modifier = Modifier.background(color = Surface),
        message = "An error occurred."
    )
}