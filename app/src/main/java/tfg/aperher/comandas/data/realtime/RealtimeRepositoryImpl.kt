package tfg.aperher.comandas.data.realtime

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.createChannel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.jsonPrimitive
import tfg.aperher.comandas.data.article.ArticleRepository
import tfg.aperher.comandas.domain.model.ArticleReady
import tfg.aperher.comandas.domain.model.State
import javax.inject.Inject

class RealtimeRepositoryImpl @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val supabaseClient: SupabaseClient
) : RealtimeRepository {

    override suspend fun listenUpdatedArticles(filterState: State?): Flow<ArticleReady> =
        withContext(Dispatchers.IO) {
            val channelId = filterState?.value ?: "channelId"
            val channel = supabaseClient.realtime.createChannel(channelId)

            val changeFlow = channel.postgresChangeFlow<PostgresAction.Update>(schema = "public") {
                table = "ComandaArticulo"
                if (filterState != null) filter = "estado=eq.${filterState.value}"
            }.map {
                val articleOrderId = it.record["id"]?.jsonPrimitive?.content ?: ""
                articleRepository.getReadyArticle(articleOrderId).getOrNull()
            }.filterNotNull()

            if (supabaseClient.realtime.status.value != Realtime.Status.CONNECTED) {
                supabaseClient.realtime.connect()
            }

            channel.join()

            changeFlow
        }

}