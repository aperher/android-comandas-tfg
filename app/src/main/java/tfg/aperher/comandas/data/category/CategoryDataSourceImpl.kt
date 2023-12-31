package tfg.aperher.comandas.data.category

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import tfg.aperher.comandas.data.section.ESTABLISHMENT_ID
import tfg.aperher.comandas.data.utils.safeApiCall
import tfg.aperher.comandas.domain.model.Category
import javax.inject.Inject
import javax.inject.Named

class CategoryDataSourceImpl @Inject constructor(@Named("restRetrofit") retrofit: Retrofit) : CategoryDataSource {
    private val retrofitCategoryService = retrofit.create(CategoryRetrofit::class.java)

    override suspend fun getCategories(): Response<List<Category>> = safeApiCall {
        retrofitCategoryService.getCategories(ESTABLISHMENT_ID)
    }

    interface CategoryRetrofit {
        @GET("categories")
        suspend fun getCategories(@Query("establishment") establishment: String): Response<List<Category>>
    }
}