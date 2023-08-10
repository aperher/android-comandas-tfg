package tfg.aperher.comandas.data.article

import retrofit2.Response
import tfg.aperher.comandas.data.article.model.ArticleDto

interface ArticleDataSource {
    suspend fun getArticles(categoryId: String): Response<List<ArticleDto>>

    suspend fun getPopularArticles(): Response<List<ArticleDto>>

    suspend fun getProminentArticles(): Response<List<ArticleDto>>

    suspend fun getArticleDetails(articleId: String): Response<ArticleDto>
}