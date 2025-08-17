package elmeniawy.eslam.nutrisport.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elmeniawy.eslam.nutrisport.data.domain.CustomerRepository
import elmeniawy.eslam.nutrisport.shared.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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
    val customer = _customerRepository.readCustomerFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )

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