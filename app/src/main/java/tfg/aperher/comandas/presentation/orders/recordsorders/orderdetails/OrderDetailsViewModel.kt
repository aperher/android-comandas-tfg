package tfg.aperher.comandas.presentation.orders.recordsorders.orderdetails

import android.util.Log
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

    fun getOrder(orderId: String) {
        viewModelScope.launch {
            Log.d("OrderDetailsViewModel", "getOrder: $orderId")
            getOrderUseCase(orderId).fold(
                onSuccess = { Log.d("OrderDetailsViewModel", "getOrder: $it"); _order.value = it },
                onFailure = { Log.d("OrderDetailsViewModel", "getOrder: $it") }
            )
            Log.d("OrderDetailsViewModel", "getOrder: ${_order.value}")
        }
    }
}