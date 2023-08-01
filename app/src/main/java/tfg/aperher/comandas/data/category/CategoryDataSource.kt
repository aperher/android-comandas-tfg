package tfg.aperher.comandas.data.category

import retrofit2.Response
import tfg.aperher.comandas.domain.model.Category

interface CategoryDataSource {
    suspend fun getCategories(): Response<List<Category>>
}