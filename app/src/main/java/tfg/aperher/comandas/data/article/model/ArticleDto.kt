package tfg.aperher.comandas.data.article.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleDto(
    val id: String,
    val name: String,
    val description: String?,
    val price: Double?,
    val image: String?
)
