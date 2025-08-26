package elmeniawy.eslam.nutrisport.di

import elmeniawy.eslam.nutrisport.admin_panel.AdminPanelViewModel
import elmeniawy.eslam.nutrisport.auth.AuthViewModel
import elmeniawy.eslam.nutrisport.cart.CartViewModel
import elmeniawy.eslam.nutrisport.data.AdminRepositoryImpl
import elmeniawy.eslam.nutrisport.data.CustomerRepositoryImp
import elmeniawy.eslam.nutrisport.data.ProductRepositoryImpl
import elmeniawy.eslam.nutrisport.data.domain.AdminRepository
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.data.domain.ProductRepository
import elmeniawy.eslam.nutrisport.home.HomeGraphViewModel
import elmeniawy.eslam.nutrisport.manage_product.ManageProductViewModel
import elmeniawy.eslam.nutrisport.product_details.ProductDetailsViewModel
import elmeniawy.eslam.nutrisport.products_overview.ProductsOverviewViewModel
import elmeniawy.eslam.nutrisport.profile.ProfileViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * KoinModule
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 11:55 AM.
 */

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImp() }
    single<AdminRepository> { AdminRepositoryImpl() }
    single<ProductRepository> { ProductRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProductsOverviewViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::ProductDetailsViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::AdminPanelViewModel)
    viewModelOf(::ManageProductViewModel)
}

expect val targetModule: Module

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, targetModule)
    }
}