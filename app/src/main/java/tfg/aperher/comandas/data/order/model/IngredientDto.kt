package tfg.aperher.comandas.data.order.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientDto(val id: String?, val name: String?, val price: Double?)
