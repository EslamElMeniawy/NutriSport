package elmeniawy.eslam.nutrisport

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize

/**
 * MyApplication
 *
 * Created by Eslam El-Meniawy on 10-Aug-2025 at 5:02 PM.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(context = this)
    }
}