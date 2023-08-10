package tfg.aperher.comandas.data.article

import tfg.aperher.comandas.domain.model.Article

interface ArticleRepository {
    suspend fun getArticles(categoryId: String): Result<List<Article>>

    suspend fun getPopularArticles(): Result<List<Article>>

    suspend fun getProminentArticles(): Result<List<Article>>

    suspend fun getArticleDetails(articleId: String): Result<Article>
}