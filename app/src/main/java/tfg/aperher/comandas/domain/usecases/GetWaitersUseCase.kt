package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.user.UserRepository
import javax.inject.Inject

class GetWaitersUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getEstablishmentWaiters()
}