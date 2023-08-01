package tfg.aperher.comandas.presentation.ordertake

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.usecases.AddArticleOrderToListUseCase
import tfg.aperher.comandas.domain.usecases.ChangeArticleQuantityUseCase
import tfg.aperher.comandas.domain.usecases.GetOrderUseCase
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.model.Table
import tfg.aperher.comandas.domain.usecases.SendOrderUseCase
import tfg.aperher.comandas.domain.util.OrderError
import tfg.aperher.comandas.utils.Event
import javax.inject.Inject

@HiltViewModel
class TakeOrderViewModel @Inject constructor(
    private val addArticleOrderToListUseCase: AddArticleOrderToListUseCase,
    private val changeQuantityUseCase: ChangeArticleQuantityUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val sendOrderUseCase: SendOrderUseCase
) : ViewModel() {

    private lateinit var initialOrder: Order
    private val currentOrder get() = initialOrder.copy(articles = _articlesInOrder.value!!)

    private val _articlesInOrder = MutableLiveData<List<ArticleInOrder>>(emptyList())
    val articlesInOrder get() = _articlesInOrder

    val totalPrice = _articlesInOrder.map { articles -> articles.sumOf { it.price * it.quantity } }
    val articleNumber = _articlesInOrder.map { articles -> articles.sumOf { it.quantity } }

    private val _invalidateElementList = MutableLiveData<Event<Int>>()
    val invalidateElementList get() = _invalidateElementList

    private val _viewState = MutableStateFlow(TakeOrderViewState())
    private val currentViewState get() = _viewState.value
    val viewState get() = _viewState

    private val _orderError = MutableLiveData<Event<OrderError>>()
    val orderError get() = _orderError

    private val _navigateToOrderActivity = MutableLiveData<Event<Boolean>>()
    val navigateToOrderActivity get() = _navigateToOrderActivity

    fun initOrder(sectionName: String, table: Table, orderActive: Boolean) {
        initialOrder = Order(tableId = table.id)
        _viewState.value = TakeOrderViewState(
            sectionName = sectionName,
            tableNumber = table.number
        )
        if (orderActive) setOrderActive()
    }

    fun addArticleToOrder(article: ArticleInOrder) {
        viewModelScope.launch {
            val articlesList = _articlesInOrder.value!!
            val (index, newList) = addArticleOrderToListUseCase(articlesList, article)

            _articlesInOrder.value = newList
            if (index != -1) _invalidateElementList.value = Event(index)
        }
    }

    fun updateArticle(articlePosition: Int, quantityChange: Int) {
        viewModelScope.launch {
            val articlesList = _articlesInOrder.value!!
            val (articleIndex, newList) = changeQuantityUseCase(articlesList, articlePosition, quantityChange)

            _articlesInOrder.value = newList
            if (articleIndex != -1) _invalidateElementList.value = Event(articleIndex)
            if (newList.isEmpty())
                _viewState.value = currentViewState.copy(showBottomSheet = Event(false))
        }
    }

    fun sendOrder() {
        viewModelScope.launch {
            _viewState.value = currentViewState.copy(progressBarLoading = true)
            Log.d("TakeOrderViewModel", "sendOrder: $currentOrder")
            val result = sendOrderUseCase(initialOrder, currentOrder)
            result.fold(
                onSuccess = { _navigateToOrderActivity.value = Event(true) },
                onFailure = { _orderError.value = Event(it as OrderError) }
            )

            _viewState.value = currentViewState.copy(progressBarLoading = false)
        }
    }

    fun showBottomSheet(shouldShow: Boolean) {
        _viewState.value = currentViewState.copy(showBottomSheet = Event(shouldShow))
    }

    fun checkOrderSaved() {
        val isSaved = initialOrder.areItemsSame(currentOrder)
        if (isSaved) _navigateToOrderActivity.value = Event(true)
        else _orderError.value = Event(OrderError.OrderNotSavedError)
    }

    private fun setOrderActive() {
        _viewState.value = currentViewState.copy(showBottomSheet = Event(true))
        viewModelScope.launch {
            val result = getOrderUseCase(initialOrder.tableId)
            result.fold(
                onSuccess = { order ->
                    initialOrder = order; _articlesInOrder.value = order.articles
                },
                onFailure = { Log.d("TakeOrderViewModel", "Order not found") }
            )
        }
    }
}
