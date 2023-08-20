package tfg.aperher.comandas.data.realtime

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.createChannel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonPrimitive
import tfg.aperher.comandas.data.article.ArticleRepository
import tfg.aperher.comandas.data.section.ESTABLISHMENT_ID
import tfg.aperher.comandas.data.utils.getTime
import tfg.aperher.comandas.domain.model.ArticleReady
import tfg.aperher.comandas.domain.model.Order
import tfg.aperher.comandas.domain.model.State
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class RealtimeRepositoryImpl @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val supabaseClient: SupabaseClient
) : RealtimeRepository {

    private val articleReadySharedFlow = MutableSharedFlow<ArticleReady>()
    private val orderUpdatesSharedFlow = MutableSharedFlow<Pair<Order, State>>()

    override suspend fun listenUpdatedArticles(): Flow<ArticleReady> {
        val channelId = ESTABLISHMENT_ID
        val channel = supabaseClient.realtime.createChannel(channelId)

        val changeFlow = channel.postgresChangeFlow<PostgresAction.Update>(schema = "public") {
            table = "ComandaArticulo"
            filter = "establecimiento_id=eq.$ESTABLISHMENT_ID"
        }.mapNotNull {
            val articleOrderId = it.record["id"]?.jsonPrimitive?.content ?: ""
            articleRepository.getReadyArticle(articleOrderId).getOrNull()
        }

        ensureConnected(channel)

        CoroutineScope(coroutineContext).launch {
            changeFlow.collect { articleReadySharedFlow.emit(it) }
        }

        return articleReadySharedFlow
    }

    override suspend fun listeningForUpdatedOrders(sectionId: String): Flow<Pair<Order, State>> {
        val channel = supabaseClient.realtime.createChannel(sectionId)

        val changeFlow = channel.postgresChangeFlow<PostgresAction.Update>(schema = "public") {
            table = "Comanda"
            filter = "seccion_id=eq.$sectionId"
        }.map {
            val orderId = it.record["id"]?.jsonPrimitive?.content ?: ""
            val tableId = it.record["mesa_id"]?.jsonPrimitive?.content ?: ""
            var initTime = it.record["fecha_hora_inicio"]?.jsonPrimitive?.content?.getTime() ?: ""
            val stateString = it.record["estado"]?.jsonPrimitive?.content ?: ""

            val isOnService = it.record["esta_en_servicio"]?.jsonPrimitive?.boolean ?: false
            val state = if (isOnService) {
                State.fromValue(stateString) ?: State.AVAILABLE
            } else {
                initTime = ""
                State.AVAILABLE
            }

            val order = Order(id = orderId, tableId = tableId, initTime = initTime)

            order to state
        }

        ensureConnected(channel)

        CoroutineScope(coroutineContext).launch {
            changeFlow.collect {
                orderUpdatesSharedFlow.emit(it)
            }
        }

        return orderUpdatesSharedFlow
    }

    private suspend fun ensureConnected(channel: RealtimeChannel) {
        if (supabaseClient.realtime.status.value != Realtime.Status.CONNECTED) {
            supabaseClient.realtime.connect()
        }
        channel.join()
    }
}