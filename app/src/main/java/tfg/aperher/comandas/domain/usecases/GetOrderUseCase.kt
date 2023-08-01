package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.order.OrderRepository
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(tableId: String) = orderRepository.getOrder(tableId)
}