package tfg.aperher.comandas.data.order

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import tfg.aperher.comandas.data.order.model.OrderDto
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(retrofit: Retrofit) : OrderDataSource {
    private val orderRetrofit = retrofit.create(OrderRetrofit::class.java)

    override suspend fun getAllOrders(): Response<List<OrderDto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrder(tableId: String): Response<OrderDto> =
        orderRetrofit.getOrder(tableId)

    override suspend fun createOrder(order: OrderDto): Response<Unit> =
        orderRetrofit.createOrder(order)

    override suspend fun updateOrder(article: OrderDto): Response<Unit> =
        orderRetrofit.updateArticleOrder(article)

    interface OrderRetrofit {
        @GET("orders")
        suspend fun getAllOrders(@Query("establishment") establishment: String): Response<List<OrderDto>>

        @GET("orders/{tableId}")
        suspend fun getOrder(@Path("tableId") tableId: String): Response<OrderDto>

        // Meterle el header del camareroId
        @POST("orders")
        suspend fun createOrder(@Body order: OrderDto): Response<Unit>

        @PUT("orders")
        suspend fun updateArticleOrder(@Body article: OrderDto): Response<Unit>
    }
}
