package tfg.aperher.comandas.data.order.model

import tfg.aperher.comandas.data.ingredient.model.toDomain
import tfg.aperher.comandas.data.ingredient.model.toDto
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.State

fun ArticleInOrderDto.toDomain() = ArticleInOrder(
    id = mutableListOf(),
    articleId = articleId,
    name = name ?: "",
    price = price ?: 0.0,
    quantity = 1,
    extras = extras?.map { it.toDomain() } ?: emptyList(),
    state = State.values().find { it.value == state } ?: State.PENDING
)

fun ArticleInOrder.toDto() = ArticleInOrderDto(
    id = null,
    articleId = articleId,
    name = name,
    extras = extras.map { it.toDto() },
    state = state.value
)