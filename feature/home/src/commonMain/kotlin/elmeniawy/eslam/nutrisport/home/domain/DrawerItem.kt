package elmeniawy.eslam.nutrisport.home.domain

import elmeniawy.eslam.nutrisport.shared.Resources
import org.jetbrains.compose.resources.DrawableResource

/**
 * DrawerItem
 *
 * Created by Eslam El-Meniawy on 12-Aug-2025 at 9:36 AM.
 */
enum class DrawerItem(val title: String, val icon: DrawableResource) {
    Profile(title = "Profile", icon = Resources.Icon.Person),
    Blog(title = "Blog", icon = Resources.Icon.Book),
    Locations(title = "Locations", icon = Resources.Icon.MapPin),
    Contact(title = "Contact Us", icon = Resources.Icon.Edit),
    SignOut(title = "Sign Out", icon = Resources.Icon.SignOut),
    Admin(title = "Admin Panel", icon = Resources.Icon.Unlock)
}