package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.order.OrderRepository
import javax.inject.Inject

class GetOrderListUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() = orderRepository.getAllOrders()
}