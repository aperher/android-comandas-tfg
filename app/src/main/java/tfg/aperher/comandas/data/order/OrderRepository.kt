package tfg.aperher.comandas.data.order

import tfg.aperher.comandas.domain.model.Order

interface OrderRepository {
    suspend fun getAllOrders() : Result<List<Order>>
    suspend fun getOrder(orderId: String) : Result<Order>
    suspend fun createOrder(order: Order) : Result<Unit>
    suspend fun updateOrder(order: Order) : Result<Unit>
}
