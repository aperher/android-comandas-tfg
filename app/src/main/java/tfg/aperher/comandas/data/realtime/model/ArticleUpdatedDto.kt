package tfg.aperher.comandas.data.realtime.model

import com.squareup.moshi.JsonClass
import tfg.aperher.comandas.data.ingredient.model.IngredientDto
import tfg.aperher.comandas.data.ingredient.model.toDomain
import tfg.aperher.comandas.domain.model.ArticleReady
import tfg.aperher.comandas.domain.model.State

@JsonClass(generateAdapter = true)
data class ArticleUpdatedDto(
    val id: String?,
    val orderId: String?,
    val name: String?,
    val section: String?,
    val table: Int? = 1,
    val status: String?,
    val extras: List<IngredientDto>? = emptyList()
) {
    fun toArticleReady() = ArticleReady(
        id = id ?: "",
        orderId = orderId ?: "",
        name = name ?: "",
        section = section ?: "",
        table = table ?: 1,
        status = State.values().find { it.value == status } ?: State.PENDING,
        extras = extras?.map { it.toDomain() } ?: emptyList()
    )
}