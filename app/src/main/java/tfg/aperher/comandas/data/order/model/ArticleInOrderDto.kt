package tfg.aperher.comandas.data.order.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleInOrderDto(
    val id: String?,
    val articleId: String,
    val name: String? = null,
    val price: Double? = null,
    val state: String,
    val extras: List<IngredientDto>? = null
)