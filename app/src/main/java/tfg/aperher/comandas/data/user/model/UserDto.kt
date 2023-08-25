package tfg.aperher.comandas.data.user.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val role: String? = null
)
