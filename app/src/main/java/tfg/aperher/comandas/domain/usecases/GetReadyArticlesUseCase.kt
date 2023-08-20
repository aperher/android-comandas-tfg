package tfg.aperher.comandas.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
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

        val updatedArticleFlow = realtimeRepository.listenUpdatedArticles()
            .transform { articleReady ->
                if (articleReady.status == State.READY || articleReady.status == State.DELIVERED)
                    emit(articleReady)
            }

        updatedArticleFlow.collect { newReadyArticle ->
            when (newReadyArticle.status) {
                State.READY -> initialReadyArticles.add(newReadyArticle)
                else -> initialReadyArticles.removeAll { it.id == newReadyArticle.id }
            }
            emit(initialReadyArticles.toList())
        }

    }.flowOn(Dispatchers.IO)
}