package tfg.aperher.comandas.presentation.orders.recordsorders.orderdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.usecases.GetOrderUseCase
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(private val getOrderUseCase: GetOrderUseCase) : ViewModel() {
    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> get() = _order

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getOrder(orderId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            getOrderUseCase(orderId).onSuccess {
                _isLoading.value = false
                _order.value = it
            }
        }
    }
}