package tfg.aperher.comandas.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.order.OrderRepository
import javax.inject.Inject

class SetServedArticleUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(articleOrderId: String) = withContext(Dispatchers.IO) {
        orderRepository.setArticleServed(articleOrderId)
    }
}