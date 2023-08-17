package tfg.aperher.comandas.data.order

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.order.model.toDomain
import tfg.aperher.comandas.data.order.model.toDto
import tfg.aperher.comandas.data.utils.response.toResult
import tfg.aperher.comandas.domain.model.Order
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderDataSource: OrderDataSource) :
    OrderRepository {
    override suspend fun getAllOrders(waiterId: String?, dateMillisUTC: Long?): Result<List<Order>> =
        withContext(Dispatchers.IO) {
            val dateTimeStampTz = dateMillisUTC?.let { millisToDate(dateMillisUTC) }
            orderDataSource.getAllOrders(waiterId, dateTimeStampTz).toResult { it.map { order -> order.toDomain() } }
        }

    override suspend fun getOrder(orderId: String): Result<Order> = withContext(Dispatchers.IO) {
        orderDataSource.getOrder(orderId).toResult { it.toDomain() }
    }

    override suspend fun createOrder(order: Order): Result<Unit> = withContext(Dispatchers.IO) {
        orderDataSource.createOrder(order.toDto()).toResult { }
    }

    override suspend fun updateOrder(order: Order): Result<Unit> = withContext(Dispatchers.IO) {
        orderDataSource.updateOrder(order.toDto()).toResult { }
    }

    override suspend fun setArticleServed(articleOrderId: String): Result<Unit> = withContext(Dispatchers.IO) {
        orderDataSource.setArticleServed(articleOrderId).toResult { }
    }

    private fun millisToDate(millis: Long): String {
        val instant = Instant.ofEpochMilli(millis)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return formatter.withZone(ZoneOffset.UTC).format(instant)
    }
}
