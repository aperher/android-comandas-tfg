package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.order.OrderRepository
import tfg.aperher.comandas.domain.model.User
import javax.inject.Inject

class GetOrderListUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(waiterId: User?, dateMillisUTC: Long?) =
        orderRepository.getAllFinishedOrders(waiterId?.id, dateMillisUTC)
}