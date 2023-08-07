package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.section.SectionRepository
import tfg.aperher.comandas.domain.model.Table
import javax.inject.Inject

class GetTablesDetailsUseCase @Inject constructor(
    private val sectionRepository: SectionRepository
) {
    suspend operator fun invoke(sectionId: String): Result<List<Table>> {
        val sectionResult = sectionRepository.getSectionById(sectionId)
        return sectionResult.map { section -> section.tables }
    }
}