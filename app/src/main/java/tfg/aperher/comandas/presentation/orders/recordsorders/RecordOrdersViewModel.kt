package tfg.aperher.comandas.presentation.orders.recordsorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.model.User
import tfg.aperher.comandas.domain.usecases.GetOrderListUseCase
import javax.inject.Inject

@HiltViewModel
class RecordOrdersViewModel @Inject constructor(private val getOrderListUseCase: GetOrderListUseCase) : ViewModel() {

    private val _orders : MutableLiveData<List<Order>> = MutableLiveData(emptyList())
    val orders: LiveData<List<Order>> get() = _orders

    val noOrdersFound = _orders.map { it.isEmpty() && !_viewState.value.isInitLoading() }

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> get() = _isError

    private val _viewState = MutableStateFlow(RecordOrdersViewState())
    val viewState: StateFlow<RecordOrdersViewState> get() = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isShimmerLoading = true)
            getOrders()
            _viewState.value = _viewState.value.copy(isShimmerLoading = false)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isPullToRefresh = true)
            getOrders()
            _viewState.value = _viewState.value.copy(isPullToRefresh = false)
        }
    }

    fun onWaiterSelected(waiter: User?) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(waiter = waiter, isProgressLoading = true)
            getOrders()
            _viewState.value = _viewState.value.copy(isProgressLoading = false)
        }
    }

    fun onDateSelected(date: Long?) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(dateMillis = date, isProgressLoading = true)
            getOrders()
            _viewState.value = _viewState.value.copy(isProgressLoading = false)
        }
    }

    private suspend fun getOrders() {
        getOrderListUseCase(_viewState.value.waiter, _viewState.value.dateMillis).fold(
            onSuccess = { _orders.value = it; _isError.value = false },
            onFailure = { _isError.value = true }
        )
    }
}
