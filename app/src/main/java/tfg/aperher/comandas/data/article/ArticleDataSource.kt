package tfg.aperher.comandas.data.article

import retrofit2.Response
import tfg.aperher.comandas.domain.model.Article

interface ArticleDataSource {
    suspend fun getArticles(categoryId: String): Response<List<Article>>

    suspend fun getArticleDetails(articleId: String): Response<Article>
}