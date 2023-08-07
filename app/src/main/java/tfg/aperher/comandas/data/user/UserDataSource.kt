package tfg.aperher.comandas.data.user

import retrofit2.Response
import tfg.aperher.comandas.data.user.model.UserDto

interface UserDataSource {
    suspend fun getEstablishmentWaiters(): Response<List<UserDto>>
}