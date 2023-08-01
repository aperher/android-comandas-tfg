package tfg.aperher.comandas.data.article

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.utils.toResult
import tfg.aperher.comandas.domain.model.Article
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val dataSource: ArticleDataSource) : ArticleRepository {
    override suspend fun getArticles(categoryId: String): Result<List<Article>> = withContext(
        Dispatchers.IO) {
        dataSource.getArticles(categoryId).toResult { it }
    }

    override suspend fun getArticleDetails(articleId: String): Result<Article> = withContext(Dispatchers.IO) {
        dataSource.getArticleDetails(articleId).toResult { it }
    }
}