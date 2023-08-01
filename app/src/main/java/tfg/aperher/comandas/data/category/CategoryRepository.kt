package tfg.aperher.comandas.data.category

import tfg.aperher.comandas.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): Result<List<Category>>
}