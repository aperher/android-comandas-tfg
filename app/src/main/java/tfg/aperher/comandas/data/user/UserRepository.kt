package tfg.aperher.comandas.data.user

import tfg.aperher.comandas.domain.model.User

interface UserRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun getEstablishmentWaiters(): Result<List<User>>
}