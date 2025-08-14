package elmeniawy.eslam.nutrisport.manage_product

import androidx.compose.runtime.Composable
import dev.gitlive.firebase.storage.File

/**
 * PhotoPicker
 *
 * Created by Eslam El-Meniawy on 14-Aug-2025 at 11:41 AM.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PhotoPicker {
    fun open()

    @Composable
    fun InitializePhotoPicker(onImageSelect: (File?) -> Unit)
}