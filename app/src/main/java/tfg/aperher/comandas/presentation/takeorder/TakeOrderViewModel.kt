package tfg.aperher.comandas.presentation.takeorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.usecases.AddArticleOrderToListUseCase
import tfg.aperher.comandas.domain.usecases.ChangeArticleQuantityUseCase
import tfg.aperher.comandas.domain.usecases.GetOrderUseCase
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.model.Table
import tfg.aperher.comandas.domain.usecases.SendOrderUseCase
import tfg.aperher.comandas.domain.usecases.SetServedArticleUseCase
import tfg.aperher.comandas.domain.util.OrderError
import tfg.aperher.comandas.utils.Event
import javax.inject.Inject

@HiltViewModel
class TakeOrderViewModel @Inject constructor(
    private val addArticleOrderToListUseCase: AddArticleOrderToListUseCase,
    private val changeQuantityUseCase: ChangeArticleQuantityUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val sendOrderUseCase: SendOrderUseCase,
    private val setServedArticleUseCase: SetServedArticleUseCase
) : ViewModel() {

    private lateinit var initialOrder: Order
    private val currentOrder get() = initialOrder.copy(articles = _articlesInOrder.value!!)

    private val _articlesInOrder = MutableLiveData<List<ArticleInOrder>>(emptyList())
    val articlesInOrder: LiveData<List<ArticleInOrder>> get() = _articlesInOrder

    val totalPrice = _articlesInOrder.map { articles -> articles.sumOf { it.price * it.quantity } }
    val articleNumber = _articlesInOrder.map { articles -> articles.sumOf { it.quantity } }

    private val _invalidateElementList = MutableLiveData<Event<Int>>()
    val invalidateElementList: LiveData<Event<Int>> get() = _invalidateElementList

    private val _viewState = MutableStateFlow(TakeOrderViewState())
    val viewState: StateFlow<TakeOrderViewState> get() = _viewState

    private val _orderError = MutableLiveData<Event<OrderError>>()
    val orderError: LiveData<Event<OrderError>> get() = _orderError

    private val _navigateToOrderActivity = MutableLiveData<Event<Boolean>>()
    val navigateToOrderActivity: LiveData<Event<Boolean>> get() = _navigateToOrderActivity

    fun initOrder(sectionName: String, table: Table) {
        initialOrder = Order(id = table.orderId, tableId = table.id, section = sectionName)
        _viewState.value = TakeOrderViewState(
            sectionName = sectionName,
            tableNumber = table.number
        )
        val orderActive = !table.orderId.isNullOrEmpty()
        if (orderActive) fetchActiveOrder(table.orderId!!)
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
                _viewState.value = _viewState.value.copy(showBottomSheet = Event(false))
        }
    }

    fun sendOrder() {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(progressBarLoading = true)

            val result = sendOrderUseCase(initialOrder, currentOrder)
            result.fold(
                onSuccess = { _navigateToOrderActivity.value = Event(true) },
                onFailure = {
                    val error = it as OrderError
                    if (error == OrderError.SendOrderError) showBottomSheet(false)
                    _orderError.value = Event(error)
                }
            )

            _viewState.value = _viewState.value.copy(progressBarLoading = false)
        }
    }

    fun showBottomSheet(shouldShow: Boolean) {
        _viewState.value = _viewState.value.copy(showBottomSheet = Event(shouldShow))
    }

    fun checkOrderSaved() {
        val isSaved = initialOrder.articles == _articlesInOrder.value!!
        if (isSaved) _navigateToOrderActivity.value = Event(true)
        else _orderError.value = Event(OrderError.OrderNotSavedError)
    }

    fun setArticlesServed(articleOrderIds: List<String?>) {
        viewModelScope.launch {
            articleOrderIds.forEach { articleOrderId ->
                articleOrderId?.let { id ->
                    setServedArticleUseCase(id)
                    fetchActiveOrder(initialOrder.id!!)
                }
            }
        }
    }

    private fun fetchActiveOrder(orderId: String) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(showBottomSheet = Event(true))

            getOrderUseCase(orderId).onSuccess { order ->
                initialOrder = order; _articlesInOrder.value = order.articles
            }
        }
    }
}
