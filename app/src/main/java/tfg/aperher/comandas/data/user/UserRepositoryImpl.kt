package tfg.aperher.comandas.data.user

import tfg.aperher.comandas.data.user.model.toDomain
import tfg.aperher.comandas.data.utils.toResult
import tfg.aperher.comandas.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override suspend fun getEstablishmentWaiters(): Result<List<User>> =
        userDataSource.getEstablishmentWaiters().toResult {
            it.map { userDto -> userDto.toDomain() }
        }
}