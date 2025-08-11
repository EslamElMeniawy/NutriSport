package elmeniawy.eslam.nutrisport.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gitlive.firebase.auth.FirebaseUser
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

/**
 * AuthViewModel
 *
 * Created by Eslam El-Meniawy on 11-Aug-2025 at 1:13 PM.
 */
class AuthViewModel(private val _customerRepository: CustomerRepository) : ViewModel() {
    fun createCustomer(
        user: FirebaseUser? = null,
        onSuccess: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _customerRepository.createCustomer(
                user = user,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }
}