package tfg.aperher.comandas.data.article

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.article.model.toDomain
import tfg.aperher.comandas.data.utils.toResult
import tfg.aperher.comandas.domain.model.Article
import tfg.aperher.comandas.domain.model.ArticleReady
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val dataSource: ArticleDataSource) :
    ArticleRepository {
    override suspend fun getArticles(categoryId: String): Result<List<Article>> = withContext(
        Dispatchers.IO
    ) {
        dataSource.getArticles(categoryId).toResult { list -> list.map { dto -> dto.toDomain() } }
    }

    override suspend fun getPopularArticles(): Result<List<Article>> = withContext(Dispatchers.IO) {
        dataSource.getPopularArticles().toResult { list -> list.map { dto -> dto.toDomain() } }
    }

    override suspend fun getProminentArticles(): Result<List<Article>> =
        withContext(Dispatchers.IO) {
            dataSource.getProminentArticles()
                .toResult { list -> list.map { dto -> dto.toDomain() } }
        }

    override suspend fun getArticleDetails(articleId: String): Result<Article> =
        withContext(Dispatchers.IO) {
            dataSource.getArticleDetails(articleId).toResult { it.toDomain() }
        }

    override suspend fun getReadyArticle(articleOrderId: String): Result<ArticleReady> =
        withContext(Dispatchers.IO) {
            dataSource.getReadyArticle(articleOrderId).toResult { it.toArticleReady() }
        }

    override suspend fun getReadyArticles(): Result<List<ArticleReady>> =
        withContext(Dispatchers.IO) {
            dataSource.getReadyArticles()
                .toResult { list -> list.map { dto -> dto.toArticleReady() } }
        }
}