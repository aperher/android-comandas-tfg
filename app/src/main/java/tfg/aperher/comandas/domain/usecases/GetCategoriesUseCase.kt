package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.category.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke() = categoryRepository.getCategories()
}