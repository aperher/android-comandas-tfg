package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.user.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String) =
        userRepository.login(email, password)
}