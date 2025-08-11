package elmeniawy.eslam.nutrisport.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

/**
 * KoinModule
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 11:55 AM.
 */

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin { config?.invoke(this) }
}