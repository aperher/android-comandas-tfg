package tfg.aperher.comandas.data.order

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.order.model.toDomain
import tfg.aperher.comandas.data.order.model.toDto
import tfg.aperher.comandas.data.utils.toResult
import tfg.aperher.comandas.domain.model.Order
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderDataSource: OrderDataSource) : OrderRepository {
    override suspend fun getAllOrders(): Result<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrder(tableId: String): Result<Order> =
        orderDataSource.getOrder(tableId).toResult { it.toDomain() }

    override suspend fun createOrder(order: Order): Result<Unit> = withContext(Dispatchers.IO) {
        orderDataSource.createOrder(order.toDto()).toResult { }
    }

    override suspend fun updateOrder(order: Order): Result<Unit> = withContext(Dispatchers.IO) {
        orderDataSource.updateOrder(order.toDto()).toResult {  }
    }
}
