package elmeniawy.eslam.nutrisport.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.shared.Resources
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.component.PrimaryButton
import elmeniawy.eslam.nutrisport.shared.component.ProfileForm
import elmeniawy.eslam.nutrisport.shared.domain.Country
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
            .background(Surface)
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        ProfileForm(
            modifier = Modifier.weight(1f),
            country = Country.Egypt,
            onCountrySelect = {},
            firstName = "Eslam",
            onFirstNameChange = {},
            lastName = "",
            onLastNameChange = {},
            email = "",
            city = "",
            onCityChange = {},
            postalCode = null,
            onPostalCodeChange = {},
            address = "",
            onAddressChange = {},
            phoneNumber = "",
            onPhoneNumberChange = {}
        )

        PrimaryButton(
            text = "Continue",
            icon = Resources.Icon.Checkmark,
            onClick = {}
        )
    }
}