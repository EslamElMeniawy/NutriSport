package elmeniawy.eslam.nutrisport.home.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.home.domain.DrawerItem
import elmeniawy.eslam.nutrisport.shared.BebasNeueFont
import elmeniawy.eslam.nutrisport.shared.FontSize
import elmeniawy.eslam.nutrisport.shared.SurfaceDarker
import elmeniawy.eslam.nutrisport.shared.TextPrimary
import elmeniawy.eslam.nutrisport.shared.TextSecondary
import elmeniawy.eslam.nutrisport.shared.domain.Customer
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * CustomDrawer
 *
 * Created by Eslam El-Meniawy on 12-Aug-2025 at 9:41 AM.
 */

@Composable
fun CustomDrawer(
    modifier: Modifier = Modifier,
    customer: RequestState<Customer>? = null,
    onProfileClick: (() -> Unit)? = null,
    onBlogClick: (() -> Unit)? = null,
    onLocationsClick: (() -> Unit)? = null,
    onContactUsClick: (() -> Unit)? = null,
    onSignOutClick: (() -> Unit)? = null,
    onAdminPanelClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "NUTRISPORT",
            textAlign = TextAlign.Center,
            color = TextSecondary,
            fontFamily = BebasNeueFont(),
            fontSize = FontSize.EXTRA_LARGE
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Healthy Lifestyle",
            textAlign = TextAlign.Center,
            color = TextPrimary,
            fontSize = FontSize.REGULAR
        )

        Spacer(modifier = Modifier.height(50.dp))

        DrawerItem.entries.take(5).forEach { drawerItem ->
            DrawerItemCard(
                drawerItem = drawerItem,
                onClick = when (drawerItem) {
                    DrawerItem.Profile -> onProfileClick
                    DrawerItem.Blog -> onBlogClick
                    DrawerItem.Locations -> onLocationsClick
                    DrawerItem.Contact -> onContactUsClick
                    DrawerItem.SignOut -> onSignOutClick
                    else -> null
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedContent(targetState = customer) { customerState ->
            if (customerState?.isSuccess() == true && customerState.getSuccessData().isAdmin == true) {
                DrawerItemCard(
                    drawerItem = DrawerItem.Admin,
                    onClick = onAdminPanelClick
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview
@Composable
private fun CustomDrawerPreview() {
    CustomDrawer(
        modifier = Modifier.fillMaxWidth().background(color = SurfaceDarker),
        customer = RequestState.Success(
            Customer(
                isAdmin = true
            )
        )
    )
}