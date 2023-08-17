package tfg.aperher.comandas.data.article

import retrofit2.Response
import tfg.aperher.comandas.data.article.model.ArticleDto
import tfg.aperher.comandas.data.realtime.model.ArticleUpdatedDto

interface ArticleDataSource {
    suspend fun getArticles(categoryId: String): Response<List<ArticleDto>>
    suspend fun getPopularArticles(): Response<List<ArticleDto>>
    suspend fun getProminentArticles(): Response<List<ArticleDto>>
    suspend fun getArticleDetails(articleId: String): Response<ArticleDto>
    suspend fun getReadyArticle(articleOrderId: String): Response<ArticleUpdatedDto>
    suspend fun getReadyArticles(): Response<List<ArticleUpdatedDto>>
}