package elmeniawy.eslam.nutrisport.profile.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.shared.Alpha
import elmeniawy.eslam.nutrisport.shared.BorderError
import elmeniawy.eslam.nutrisport.shared.BorderIdle
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.SurfaceLighter
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * CustomTextField
 *
 * Created by Eslam El-Meniawy on 12-Aug-2025 at 12:56 PM.
 */

@Composable
@Preview
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: ((String) -> Unit)? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    error: Boolean = false,
    expanded: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    val borderColor by animateColorAsState(
        targetValue = if (error) BorderError else BorderIdle
    )

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(size = 6.dp)
            ),
        value = value,
        onValueChange = onValueChange ?: {},
        enabled = enabled,
        placeholder = if (placeholder != null) {
            {
                Text(
                    modifier = Modifier.alpha(Alpha.HALF),
                    text = placeholder,
                    fontSize = FontSize.REGULAR
                )
            }
        } else null,
        singleLine = !expanded,
        shape = RoundedCornerShape(size = 6.dp),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SurfaceLighter,
            unfocusedContainerColor = SurfaceLighter,
            disabledContainerColor = SurfaceLighter,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            disabledTextColor = TextPrimary.copy(alpha = Alpha.DISABLED)
        )
    )
}