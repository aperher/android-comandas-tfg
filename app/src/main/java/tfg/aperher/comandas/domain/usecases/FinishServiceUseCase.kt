package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.order.OrderRepository
import tfg.aperher.comandas.domain.model.Table
import javax.inject.Inject

class FinishServiceUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(tables: List<Table>) = orderRepository.finishOrdersService(tables)
}