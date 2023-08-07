package tfg.aperher.comandas.data.user.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(val id: String?, val name: String?, val email: String?, val password: String?, val role: String?)
