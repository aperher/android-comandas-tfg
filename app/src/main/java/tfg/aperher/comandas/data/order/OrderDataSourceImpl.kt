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
import tfg.aperher.comandas.data.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Named

class OrderDataSourceImpl @Inject constructor(@Named("restRetrofit") retrofit: Retrofit) :
    OrderDataSource {

    private val orderRetrofit = retrofit.create(OrderRetrofit::class.java)

    override suspend fun getAllFinishedOrders(
        userId: String?,
        date: String?
    ): Response<List<OrderDto>> = safeApiCall {
        orderRetrofit.getAllFinishedOrders(ESTABLISHMENT_ID, userId, date)
    }

    override suspend fun getOrder(orderId: String): Response<OrderDto> = safeApiCall {
        orderRetrofit.getOrder(orderId)
    }

    override suspend fun createOrder(order: OrderDto): Response<Unit> = safeApiCall {
        orderRetrofit.createOrder(order)
    }

    override suspend fun updateOrder(article: OrderDto): Response<Unit> = safeApiCall {
        orderRetrofit.updateArticleOrder(article)
    }

    override suspend fun setArticleServed(articleOrderId: String): Response<Unit> = safeApiCall {
        orderRetrofit.setArticleServed(articleOrderId)
    }

    override suspend fun finishOrdersService(tablesId: List<String>): Response<Unit> = safeApiCall {
        orderRetrofit.finishOrdersService(tablesId)
    }

    interface OrderRetrofit {
        @GET("orders")
        suspend fun getAllFinishedOrders(
            @Query("establishmentId") establishment: String,
            @Query("userId") userIdFilter: String?,
            @Query("date") dateFilter: String?
        ): Response<List<OrderDto>>

        @GET("orders/{orderId}")
        suspend fun getOrder(@Path("orderId") orderId: String): Response<OrderDto>

        @POST("orders")
        suspend fun createOrder(@Body order: OrderDto): Response<Unit>

        @PUT("orders")
        suspend fun updateArticleOrder(@Body article: OrderDto): Response<Unit>

        @PUT("orders/servearticle/{articleOrderId}")
        suspend fun setArticleServed(@Path("articleOrderId") articleOrderId: String): Response<Unit>

        @POST("orders/finishservice")
        suspend fun finishOrdersService(@Body tablesId: List<String>): Response<Unit>
    }
}
