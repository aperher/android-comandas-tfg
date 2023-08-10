package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.order.OrderRepository
import tfg.aperher.comandas.domain.model.Order
import javax.inject.Inject

class SendOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(initialOrder: Order, orderToSend: Order): Result<Unit> {
        return when {
            orderToSend.isEmpty() -> Result.failure(OrderError.EmptyOrderError)
            initialOrder.articles == orderToSend.articles -> Result.failure(OrderError.SameOrderError)
            else -> {
                val isNewOrder = orderToSend.id == null
                val result = if (isNewOrder) {
                    orderRepository.createOrder(orderToSend)
                } else {
                    orderRepository.updateOrder(orderToSend)
                }

                return if (result.isSuccess) result
                else Result.failure(OrderError.SendOrderError)
            }
        }
    }
}

sealed class OrderError : Exception() {
    object EmptyOrderError : OrderError()
    object OrderNotSavedError : OrderError()
    object SameOrderError : OrderError()
    object SendOrderError : OrderError()
}
