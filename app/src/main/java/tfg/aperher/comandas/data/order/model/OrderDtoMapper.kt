package tfg.aperher.comandas.data.order.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.utils.getDate
import tfg.aperher.comandas.data.utils.getTime
import tfg.aperher.comandas.domain.model.Order

suspend fun Order.toDto() = withContext(Dispatchers.Default) {
    OrderDto(
        id = id,
        table = table,
        articles = articles.map { it.id.map { id -> it.toDto().copy(id = id) } }.flatten()
    )
}

suspend fun OrderDto.toDomain() = withContext(Dispatchers.Default) {
    Order(
        id = id,
        table = table!!,
        section = section ?: "no info",
        day = init_time?.getDate() ?: "no info",
        initTime = init_time?.getTime() ?: "no info",
        endTime = end_time?.getTime() ?: "no info",
        articles = articles?.groupBy { Triple(it.articleId, it.state, it.extras) }
            ?.map { (_, value) ->
                val firstEntry = value.first()
                firstEntry.toDomain().copy(
                    id = value.map { it.id }.toMutableList(),
                    price = value.size * firstEntry.price!!,
                    quantity = value.size
                )
            } ?: emptyList()
    )
}