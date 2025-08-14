package elmeniawy.eslam.nutrisport.di

import elmeniawy.eslam.nutrisport.manage_product.PhotoPicker
import org.koin.dsl.module

actual val targetModule = module {
    single<PhotoPicker> { PhotoPicker() }
}