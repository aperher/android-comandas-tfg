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
import javax.inject.Inject

@HiltViewModel
class TablesViewModel @Inject constructor(private val getTablesSectionUseCase: GetTablesDetailsUseCase) : ViewModel() {

    private val _tables: MutableLiveData<List<Table>> = MutableLiveData()
    val tables: LiveData<List<Table>> = _tables

    val isLoading = _tables.map { it.isNullOrEmpty() }

    fun setSectionId(sectionId: String) {
        viewModelScope.launch {
            getTablesSectionUseCase(sectionId).collect { tables ->
                _tables.value = tables
            }
        }
    }
}