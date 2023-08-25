package tfg.aperher.comandas.data.order

import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.model.Table

interface OrderRepository {
    suspend fun getAllFinishedOrders(waiterId: String?, dateMillisUTC: Long?) : Result<List<Order>>
    suspend fun getOrder(orderId: String) : Result<Order>
    suspend fun createOrder(order: Order) : Result<Unit>
    suspend fun updateOrder(order: Order) : Result<Unit>
    suspend fun setArticleServed(articleOrderId: String) : Result<Unit>
    suspend fun finishOrdersService(tables: List<Table>) : Result<Unit>
}
