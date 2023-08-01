package tfg.aperher.comandas.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(val id: String, val name: String, val description: String, val imageURL: String)