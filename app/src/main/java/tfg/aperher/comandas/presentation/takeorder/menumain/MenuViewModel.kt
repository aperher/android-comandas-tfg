package tfg.aperher.comandas.presentation.takeorder.menumain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.model.Category
import tfg.aperher.comandas.domain.usecases.GetCategoriesUseCase
import tfg.aperher.comandas.utils.Event
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(getCategoriesUseCase: GetCategoriesUseCase) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _exception = MutableLiveData<Event<Throwable>>()
    val exception: LiveData<Event<Throwable>> get() = _exception

    init {
        viewModelScope.launch {
            getCategoriesUseCase().fold(
                onSuccess = { _categories.value = it },
                onFailure = { _exception.value = Event(it) }
            )
        }
    }
}