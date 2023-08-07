package tfg.aperher.comandas.presentation.orders.currentorders.tablespersection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.usecases.GetTablesDetailsUseCase
import tfg.aperher.comandas.domain.model.Table
import tfg.aperher.comandas.utils.Event
import javax.inject.Inject

@HiltViewModel
class TablesViewModel @Inject constructor(private val tablesSectionUseCase: GetTablesDetailsUseCase) : ViewModel() {

    private val _tables = MutableLiveData<List<Table>>()
    val tables: LiveData<List<Table>> get() = _tables

    val isLoading = _tables.map { it == null }

    private val _exception = MutableLiveData<Event<Throwable?>>()
    val exception: LiveData<Event<Throwable?>> get() = _exception

    fun getTables(sectionId: String) {
        viewModelScope.launch {
            tablesSectionUseCase(sectionId).fold(
                onSuccess = { _tables.value = it },
                onFailure = { _exception.value = Event(it) }
            )
        }
    }
}