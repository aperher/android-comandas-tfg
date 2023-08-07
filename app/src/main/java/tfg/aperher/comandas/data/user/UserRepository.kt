package tfg.aperher.comandas.data.user

import tfg.aperher.comandas.domain.model.User

interface UserRepository {
    suspend fun getEstablishmentWaiters(): Result<List<User>>
}