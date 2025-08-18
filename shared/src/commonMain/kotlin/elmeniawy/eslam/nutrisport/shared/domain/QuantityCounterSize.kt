package elmeniawy.eslam.nutrisport.shared.domain

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * QuantityCounterSize
 *
 * Created by Eslam El-Meniawy on 18-Aug-2025 at 11:27 AM.
 */
enum class QuantityCounterSize(
    val spacing: Dp,
    val padding: Dp
) {
    Small(
        spacing = 4.dp,
        padding = 8.dp
    ),
    Large(
        spacing = 8.dp,
        padding = 12.dp
    )
}