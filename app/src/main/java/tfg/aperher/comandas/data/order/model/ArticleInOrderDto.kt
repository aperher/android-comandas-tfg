package tfg.aperher.comandas.data.order.model

import com.squareup.moshi.JsonClass
import tfg.aperher.comandas.data.ingredient.model.IngredientDto

@JsonClass(generateAdapter = true)
data class ArticleInOrderDto(
    val id: String?,
    val articleId: String,
    val name: String? = null,
    val price: Double? = null,
    val state: String,
    val extras: List<IngredientDto>? = null
)