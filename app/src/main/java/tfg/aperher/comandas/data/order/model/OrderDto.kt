package tfg.aperher.comandas.data.order.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderDto(
    val id: String?,
    val table: Int? = null,
    val section: String? = null,
    val init_time: String? = null,
    val end_time: String? = null,
    val articles: List<ArticleInOrderDto>? = null,
)