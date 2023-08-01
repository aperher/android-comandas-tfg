package tfg.aperher.comandas.presentation.orderstables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.usecases.GetSectionsUseCase
import tfg.aperher.comandas.domain.model.Section
import tfg.aperher.comandas.utils.Event
import javax.inject.Inject

@HiltViewModel
class SectionTabsViewModel @Inject constructor(
    private val getSectionsUseCase : GetSectionsUseCase,
) : ViewModel() {

    private val _sections = MutableLiveData<List<Section>>()
    val sections: LiveData<List<Section>>
        get() = _sections

    val isScrollableTab = _sections.map { it.size > 5 }

    private val _exception = MutableLiveData<Event<Throwable?>>()
    val exception: LiveData<Event<Throwable?>>
        get() = _exception

    init {
        viewModelScope.launch {
            getSectionsUseCase().fold(
                onSuccess = { _sections.value = it },
                onFailure = { _exception.value = Event(it) }
            )
        }
    }
}