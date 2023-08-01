package tfg.aperher.comandas.data.order.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderDto(
    val id: String?,
    val table: String? = null,
    val dateTime: String? = null,
    val articles: List<ArticleInOrderDto> = emptyList(),
)