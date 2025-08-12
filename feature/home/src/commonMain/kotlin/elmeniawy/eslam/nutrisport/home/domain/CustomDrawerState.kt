package elmeniawy.eslam.nutrisport.home.domain

/**
 * CustomDrawerState
 *
 * Created by Eslam El-Meniawy on 12-Aug-2025 at 9:33 AM.
 */
enum class CustomDrawerState {
    Opened,
    Closed
}

fun CustomDrawerState.isOpened(): Boolean = this == CustomDrawerState.Opened

fun CustomDrawerState.opposite(): CustomDrawerState =
    if (this == CustomDrawerState.Opened) CustomDrawerState.Closed else CustomDrawerState.Opened