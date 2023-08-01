package tfg.aperher.comandas.data.article

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tfg.aperher.comandas.domain.model.Article
import javax.inject.Inject

class ArticleDataSourceImpl @Inject constructor(retrofit: Retrofit) : ArticleDataSource {
    private val retrofitArticleService = retrofit.create(ArticleRetrofit::class.java)

    override suspend fun getArticles(categoryId: String): Response<List<Article>> =
        retrofitArticleService.getArticles(categoryId)

    override suspend fun getArticleDetails(articleId: String): Response<Article> =
        retrofitArticleService.getArticleDetails(articleId)

    interface ArticleRetrofit {
        @GET("articles")
        suspend fun getArticles(@Query("category") category: String): Response<List<Article>>

        @GET("articles/{id}")
        suspend fun getArticleDetails(@Path("id") articleId: String): Response<Article>
    }
}