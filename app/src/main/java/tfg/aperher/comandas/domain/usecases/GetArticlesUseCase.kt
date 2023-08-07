package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.article.ArticleRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val articleRepository: ArticleRepository) {
    suspend operator fun invoke(categoryId: String) = articleRepository.getArticles(categoryId)
}