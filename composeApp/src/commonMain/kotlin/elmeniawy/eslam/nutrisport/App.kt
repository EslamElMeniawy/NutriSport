package elmeniawy.eslam.nutrisport

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.navigation.SetupNavGraph
import elmeniawy.eslam.nutrisport.shared.Constants
import elmeniawy.eslam.nutrisport.shared.Surface
import elmeniawy.eslam.nutrisport.shared.navigation.Screen
import org.koin.compose.koinInject

@Composable
fun App() {
    MaterialTheme(colorScheme = lightColorScheme(background = Surface)) {
        val customerRepository = koinInject<CustomerRepository>()
        var appReady by remember { mutableStateOf(false) }
        val isUserAuthenticated = remember { customerRepository.getCurrentUserId() != null }

        val startDestination = remember {
            if (isUserAuthenticated) Screen.HomeGraph else Screen.Auth
        }

        LaunchedEffect(Unit) {
            GoogleAuthProvider.create(
                credentials = GoogleAuthCredentials(serverId = Constants.WEB_CLIENT_ID)
            )

            appReady = true
        }

        AnimatedVisibility(modifier = Modifier.fillMaxSize(), visible = appReady) {
            SetupNavGraph(startDestination = startDestination)
        }
    }
}