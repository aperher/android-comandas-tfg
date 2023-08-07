package tfg.aperher.comandas.data.user.model

import tfg.aperher.comandas.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id ?: "",
        name = name ?: "",
        email = email ?: "",
        password = password ?: "",
        role = role ?: ""
    )
}

fun User.toData(): UserDto {
    return UserDto(
        id = id,
        name = name,
        email = email,
        password = password,
        role = role
    )
}