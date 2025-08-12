@file:OptIn(ExperimentalForeignApi::class)

package elmeniawy.eslam.nutrisport.shared.util

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

actual fun getScreenWidth(): Float {
    val bounds: CValue<CGRect> = UIScreen.mainScreen.bounds
    return CGRectGetWidth(bounds).toFloat()
}