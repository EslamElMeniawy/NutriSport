package elmeniawy.eslam.nutrisport

import androidx.compose.ui.window.ComposeUIViewController
import elmeniawy.eslam.nutrisport.di.initializeKoin

fun MainViewController() = ComposeUIViewController(configure = { initializeKoin() }) { App() }