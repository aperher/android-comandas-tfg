package tfg.aperher.comandas.data.realtime

import kotlinx.coroutines.flow.Flow
import tfg.aperher.comandas.domain.model.ArticleReady
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.model.State

interface RealtimeRepository {
    suspend fun listenUpdatedArticles(): Flow<ArticleReady>
    suspend fun listeningForUpdatedOrders(sectionId: String): Flow<Pair<Order, State>>
}