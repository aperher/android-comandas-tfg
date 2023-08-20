package tfg.aperher.comandas.data.article

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tfg.aperher.comandas.data.article.model.ArticleDto
import tfg.aperher.comandas.data.realtime.model.ArticleUpdatedDto
import tfg.aperher.comandas.data.section.ESTABLISHMENT_ID
import tfg.aperher.comandas.data.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Named

class ArticleDataSourceImpl @Inject constructor(@Named("restRetrofit") retrofit: Retrofit) :
    ArticleDataSource {
    private val retrofitArticleService = retrofit.create(ArticleRetrofit::class.java)

    override suspend fun getArticles(categoryId: String): Response<List<ArticleDto>> = safeApiCall {
        retrofitArticleService.getArticles(categoryId)
    }

    override suspend fun getPopularArticles(): Response<List<ArticleDto>> = safeApiCall {
        retrofitArticleService.getPopularArticles(ESTABLISHMENT_ID)
    }

    override suspend fun getProminentArticles(): Response<List<ArticleDto>> = safeApiCall {
        retrofitArticleService.getProminentArticles(ESTABLISHMENT_ID)
    }

    override suspend fun getArticleDetails(articleId: String): Response<ArticleDto> = safeApiCall {
        retrofitArticleService.getArticleDetails(articleId)
    }

    override suspend fun getReadyArticle(articleOrderId: String): Response<ArticleUpdatedDto> =
        safeApiCall {
            retrofitArticleService.getReadyArticle(articleOrderId)
        }


    override suspend fun getReadyArticles(): Response<List<ArticleUpdatedDto>> = safeApiCall {
        retrofitArticleService.getReadyArticles(ESTABLISHMENT_ID)
    }

    interface ArticleRetrofit {
        @GET("articles")
        suspend fun getArticles(@Query("category") category: String): Response<List<ArticleDto>>

        @GET("articles/popular_articles")
        suspend fun getPopularArticles(@Query("establishmentId") establishmentId: String): Response<List<ArticleDto>>

        @GET("articles/prominent_articles")
        suspend fun getProminentArticles(@Query("establishmentId") establishmentId: String): Response<List<ArticleDto>>

        @GET("articles/{id}")
        suspend fun getArticleDetails(@Path("id") articleId: String): Response<ArticleDto>

        @GET("articles/ready/{id}")
        suspend fun getReadyArticle(@Path("id") articleOrderId: String): Response<ArticleUpdatedDto>

        @GET("articles/ready/establishment/{id}")
        suspend fun getReadyArticles(@Path("id") establishmentId: String): Response<List<ArticleUpdatedDto>>
    }
}