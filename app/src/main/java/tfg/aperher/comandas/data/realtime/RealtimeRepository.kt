package tfg.aperher.comandas.data.realtime

import kotlinx.coroutines.flow.Flow
import tfg.aperher.comandas.domain.model.ArticleReady
import tfg.aperher.comandas.domain.model.State

interface RealtimeRepository {
    suspend fun listenUpdatedArticles(filterState: State? = null): Flow<ArticleReady>
}