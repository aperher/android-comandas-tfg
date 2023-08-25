package tfg.aperher.comandas.data.order

import retrofit2.Response
import tfg.aperher.comandas.data.order.model.OrderDto

interface OrderDataSource {
    suspend fun getAllFinishedOrders(userId: String?, date: String?) : Response<List<OrderDto>>
    suspend fun getOrder(orderId: String) : Response<OrderDto>
    suspend fun createOrder(order: OrderDto) : Response<Unit>
    suspend fun updateOrder(article: OrderDto) : Response<Unit>
    suspend fun setArticleServed(articleOrderId: String) : Response<Unit>
    suspend fun finishOrdersService(tablesId: List<String>) : Response<Unit>
}
