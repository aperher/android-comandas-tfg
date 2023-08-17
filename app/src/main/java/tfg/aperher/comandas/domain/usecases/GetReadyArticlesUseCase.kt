package tfg.aperher.comandas.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.merge
import tfg.aperher.comandas.data.article.ArticleRepository
import tfg.aperher.comandas.data.realtime.RealtimeRepository
import tfg.aperher.comandas.domain.model.ArticleReady
import tfg.aperher.comandas.domain.model.State
import javax.inject.Inject

class GetReadyArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val realtimeRepository: RealtimeRepository
) {
    operator fun invoke(): Flow<List<ArticleReady>> = flow {
        val initialReadyArticles =
            articleRepository.getReadyArticles().getOrElse { emptyList() }.toMutableList()
        emit(initialReadyArticles.toList())

        val readyArticleFlow = realtimeRepository.listenUpdatedArticles(State.READY)
        val deliveredArticleFlow = realtimeRepository.listenUpdatedArticles(State.DELIVERED)
        val updatedArticleFlow = merge(readyArticleFlow, deliveredArticleFlow)

        updatedArticleFlow.collect { newReadyArticle ->
            when (newReadyArticle.status) {
                State.DELIVERED -> initialReadyArticles.removeAll { it.id == newReadyArticle.id }
                State.READY -> initialReadyArticles.add(newReadyArticle)
                else -> {}
            }
            emit(initialReadyArticles.toList())
        }
    }.flowOn(Dispatchers.IO)
}