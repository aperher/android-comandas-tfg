package tfg.aperher.comandas.domain.usecases

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.order.OrderRepository
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.util.OrderError
import javax.inject.Inject

class SendOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(initialOrder: Order, orderToSend: Order): Result<Unit> =
        withContext(Dispatchers.IO) {
            when {
                orderToSend.isEmpty() -> Result.failure(OrderError.EmptyOrderError)
                initialOrder.articles == orderToSend.articles -> Result.failure(OrderError.SameOrderError)
                else -> sendOrder(orderToSend)
            }
        }

    private suspend fun sendOrder(order: Order): Result<Unit> {
        val result = if (order.id == null) {
            orderRepository.createOrder(order)
        } else {
            updateOrder(order)
        }
        Log.d("SendOrderUseCase", "sendOrder: ${order}")

        return if (result.isSuccess) result
        else Result.failure(OrderError.SendOrderError)
    }

    private suspend fun updateOrder(order: Order): Result<Unit> =
        orderRepository.updateOrder(sanitizeArticlesId(order))

    private fun sanitizeArticlesId(order: Order): Order =
        order.copy(articles = order.articles.map { article ->
            val idList = article.id
            val quantity = article.quantity
            val idListSize = idList.size

            val difference = quantity - idListSize
            val newIdList = if (difference > 0) {
                idList.plus(List(difference) { null })
            } else {
                idList.dropLast(-difference)
            }

            article.copy(id = newIdList.toList())
        })
}