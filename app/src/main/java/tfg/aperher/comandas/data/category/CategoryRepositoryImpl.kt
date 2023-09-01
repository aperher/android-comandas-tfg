package tfg.aperher.comandas.data.category

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.utils.toResult
import tfg.aperher.comandas.domain.model.Category
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
) : CategoryRepository {
    override suspend fun getCategories(): Result<List<Category>> = withContext(Dispatchers.IO) {
        categoryDataSource.getCategories().toResult { it }
    }
}
