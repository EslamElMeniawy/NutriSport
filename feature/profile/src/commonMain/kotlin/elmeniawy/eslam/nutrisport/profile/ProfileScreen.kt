package elmeniawy.eslam.nutrisport.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.component.PrimaryButton
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * ProfileScreen
 *
 * Created by Eslam El-Meniawy on 12-Aug-2025 at 2:37 PM.
 */

@Composable
@Preview
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        PrimaryButton(
            text = "Continue",
            icon = Resources.Icon.Checkmark,
            onClick = {}
        )
    }
}