package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.order.OrderRepository
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.util.OrderErrorException
import javax.inject.Inject

class SendOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(initialOrder: Order, orderToSend: Order): Result<Unit> {
        return when {
            orderToSend.isEmpty() -> Result.failure(OrderErrorException.EmptyOrderError)
            initialOrder.articles == orderToSend.articles -> Result.failure(OrderErrorException.SameOrderError)
            else -> {
                val isNewOrder = orderToSend.id == null
                val result = if (isNewOrder) {
                    orderRepository.createOrder(orderToSend)
                } else {
                    orderRepository.updateOrder(orderToSend)
                }

                return if (result.isSuccess) result
                else Result.failure(OrderErrorException.SendOrderError)
            }
        }
    }
}