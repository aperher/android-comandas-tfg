package tfg.aperher.comandas.presentation.orders.recordsorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.model.User
import tfg.aperher.comandas.domain.usecases.GetWaitersUseCase
import javax.inject.Inject

@HiltViewModel
class WaitersBottomSheetViewModel @Inject constructor(private val getWaitersUseCase: GetWaitersUseCase) : ViewModel() {

    private val _waiters = MutableLiveData<List<User>>()
    val waiters : LiveData<List<User>> get() = _waiters

    init {
        viewModelScope.launch {
            _waiters.value = getWaitersUseCase().fold(
                onSuccess = { it },
                onFailure = { emptyList() }
            )
        }
    }
}