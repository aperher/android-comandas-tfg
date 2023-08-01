package tfg.aperher.comandas.data.order.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.domain.model.Order

suspend fun Order.toDto() = withContext(Dispatchers.Default) {
    OrderDto(
        id = id,
        table = tableId,
        articles = articles.map { it.id.map { id -> it.toDto().copy(id = id) } }.flatten(),
        dateTime = initTime
    )
}

suspend fun OrderDto.toDomain() = withContext(Dispatchers.Default) {
    Order(
        id = id,
        tableId = table!!,
        initTime = dateTime,
        articles = articles.groupBy { Triple(it.articleId, it.state, it.extras) }
            .map { (_, value) ->
                val firstEntry = value.first()
                firstEntry.toDomain().copy(
                    id = value.map { it.id }.toMutableList(),
                    price = value.size * firstEntry.price!!,
                    quantity = value.size
                )
            }
    )
}