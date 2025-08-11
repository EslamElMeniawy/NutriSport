package elmeniawy.eslam.nutrisport.di

import elmeniawy.eslam.nutrisport.auth.AuthViewModel
import elmeniawy.eslam.nutrisport.data.CustomerRepositoryImp
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * KoinModule
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 11:55 AM.
 */

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImp() }
    viewModelOf(::AuthViewModel)
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}