package elmeniawy.eslam.nutrisport

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import elmeniawy.eslam.nutrisport.di.initializeKoin
import org.koin.android.ext.koin.androidContext

/**
 * MyApplication
 *
 * Created by Eslam El-Meniawy on 10-Aug-2025 at 5:02 PM.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(config = { androidContext(this@MyApplication) })
        Firebase.initialize(context = this)
    }
}