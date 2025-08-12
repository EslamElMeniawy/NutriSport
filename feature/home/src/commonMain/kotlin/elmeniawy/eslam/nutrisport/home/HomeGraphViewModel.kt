package elmeniawy.eslam.nutrisport.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * HomeGraphViewModel
 *
 * Created by Eslam El-Meniawy on 12-Aug-2025 at 11:49 AM.
 */
class HomeGraphViewModel(
    private val _customerRepository: CustomerRepository
) : ViewModel() {
    fun signOut(
        onSuccess: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                _customerRepository.signOut()
            }

            if (result.isSuccess()) {
                onSuccess?.invoke()
            } else if (result.isError()) {
                onError?.invoke(result.getErrorMessage())
            }
        }
    }
}