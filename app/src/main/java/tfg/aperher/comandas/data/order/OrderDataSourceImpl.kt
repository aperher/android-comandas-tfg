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
import tfg.aperher.comandas.data.section.ESTABLISHMENT_ID
import javax.inject.Inject
import javax.inject.Named

class OrderDataSourceImpl @Inject constructor(@Named("restRetrofit") retrofit: Retrofit) : OrderDataSource {
    private val orderRetrofit = retrofit.create(OrderRetrofit::class.java)

    override suspend fun getAllOrders(): Response<List<OrderDto>> =
        orderRetrofit.getAllOrders(ESTABLISHMENT_ID)

    override suspend fun getOrder(orderId: String): Response<OrderDto> =
        orderRetrofit.getOrder(orderId)

    override suspend fun createOrder(order: OrderDto): Response<Unit> =
        orderRetrofit.createOrder(order)

    override suspend fun updateOrder(article: OrderDto): Response<Unit> =
        orderRetrofit.updateArticleOrder(article)

    interface OrderRetrofit {
        @GET("orders")
        suspend fun getAllOrders(@Query("establishmentId") establishment: String): Response<List<OrderDto>>

        @GET("orders/{orderId}")
        suspend fun getOrder(@Path("orderId") orderId: String): Response<OrderDto>

        // Meterle el header del camareroId
        @POST("orders")
        suspend fun createOrder(@Body order: OrderDto): Response<Unit>

        @PUT("orders")
        suspend fun updateArticleOrder(@Body article: OrderDto): Response<Unit>
    }
}