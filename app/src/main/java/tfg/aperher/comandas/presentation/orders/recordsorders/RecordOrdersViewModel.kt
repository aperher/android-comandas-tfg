package tfg.aperher.comandas.presentation.orders.recordsorders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.model.User
import tfg.aperher.comandas.domain.usecases.GetOrderListUseCase
import javax.inject.Inject

@HiltViewModel
class RecordOrdersViewModel @Inject constructor(private val getOrderListUseCase: GetOrderListUseCase) : ViewModel() {

    private val _orders : MutableLiveData<List<Order>> = MutableLiveData()
    val orders: LiveData<List<Order>> get() = _orders

    private val _filterWaiter = MutableLiveData<User?>(null)
    val filterWaiter: LiveData<User?> get() = _filterWaiter

    private val _filterDate = MutableLiveData<String?>(null)
    val filterDate: LiveData<String?> get() = _filterDate

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _isLoadingShimmer = MutableLiveData(false)
    val isLoadingShimmer: LiveData<Boolean> get() = _isLoadingShimmer

    init {
        viewModelScope.launch {
            _isLoadingShimmer.value = true
            getOrders()
            _isLoadingShimmer.value = false
        }
    }

    fun refresh() {
        viewModelScope.launch {
            getOrders()
        }
    }

    fun onWaiterSelected(waiter: User) {
        _filterWaiter.value = waiter
    }

    fun onDateSelected(date: Long?) {
        Log.d("RecordOrdersViewModel", "Date: $date")
    }

    private suspend fun getOrders() {
        _isRefreshing.value = true
        getOrderListUseCase().fold(
            onSuccess = { _orders.value = it },
            onFailure = { Log.d("RecordOrdersViewModel", "Error: ${it.message}") }
        )
        _isRefreshing.value = false
    }
}