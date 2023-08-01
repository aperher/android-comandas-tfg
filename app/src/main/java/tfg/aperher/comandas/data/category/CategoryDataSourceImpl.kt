package tfg.aperher.comandas.data.category

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import tfg.aperher.comandas.data.sections.ESTABLISHMENT_ID
import tfg.aperher.comandas.domain.model.Category
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(retrofit: Retrofit) : CategoryDataSource {
    private val retrofitCategoryService = retrofit.create(CategoryRetrofit::class.java)

    override suspend fun getCategories(): Response<List<Category>> =
        retrofitCategoryService.getCategories(ESTABLISHMENT_ID)

    interface CategoryRetrofit {
        @GET("categories")
        suspend fun getCategories(@Query("establishment") establishment: String): Response<List<Category>>
    }
}