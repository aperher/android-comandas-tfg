package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.article.ArticleRepository
import javax.inject.Inject
import kotlinx.coroutines.*
import tfg.aperher.comandas.domain.model.Article

class GetSuggestionsUseCase @Inject constructor(private val articleRepository: ArticleRepository) {
    suspend operator fun invoke(): Pair<Result<List<Article>>, Result<List<Article>>> =
        coroutineScope {
            val popularDishesDeferred = async { articleRepository.getPopularArticles() }
            val prominentDishesDeferred = async { articleRepository.getProminentArticles() }

            val popularDishes = popularDishesDeferred.await()
            val prominentDishes = prominentDishesDeferred.await()

            return@coroutineScope Pair(popularDishes, prominentDishes)
        }
}
