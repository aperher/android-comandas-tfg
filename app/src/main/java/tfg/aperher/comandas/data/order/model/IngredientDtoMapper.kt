package tfg.aperher.comandas.data.order.model

import tfg.aperher.comandas.domain.model.Ingredient

fun IngredientDto.toDomain() = Ingredient(
    id = id ?: "",
    name = name ?: "",
    price = price ?: 0.0
)

fun Ingredient.toDto() = IngredientDto(
    id = id,
    name = name,
    price = price
)