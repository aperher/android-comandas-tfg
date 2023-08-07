package tfg.aperher.comandas.data.order

import retrofit2.Response
import tfg.aperher.comandas.data.order.model.OrderDto

interface OrderDataSource {
    suspend fun getAllOrders() : Response<List<OrderDto>>
    suspend fun getOrder(orderId: String) : Response<OrderDto>
    suspend fun createOrder(order: OrderDto) : Response<Unit>
    suspend fun updateOrder(article: OrderDto) : Response<Unit>
}
