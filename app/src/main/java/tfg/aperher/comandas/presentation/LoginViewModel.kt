package tfg.aperher.comandas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.utils.Event
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(/*private val loginUseCase: LoginUseCase*/) : ViewModel() {
    private var email: String = ""
    private var password: String = ""

    private val _goToMain = MutableLiveData<Event<Boolean>>()
    val goToMain : LiveData<Event<Boolean>> = _goToMain

    private val _exception = MutableLiveData<Event<Throwable>>()
    val exception : LiveData<Event<Throwable>> = _exception

    fun onEmailChanged(email: String) {
        this.email = email
    }

    fun onPasswordChanged(password: String) {
        this.password = password
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _goToMain.value = Event(true)
            /*loginUseCase(email, password).fold(
                onSuccess = { _goToMain.value = Event(true) },
                onFailure = { error -> _exception.value = Event(error) }
            )*/
        }
    }
}