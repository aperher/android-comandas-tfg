package tfg.aperher.comandas.data.article.model

import tfg.aperher.comandas.domain.model.Article

fun ArticleDto.toDomain() = Article(
    id = id,
    name = name,
    description = description ?: "",
    price = price ?: 0.0,
    image = image ?: ""
)

fun Article.toDto() = ArticleDto(
    id = id,
    name = name,
    description = description,
    price = price,
    image = image
)