package tfg.aperher.comandas.domain.usecases

import tfg.aperher.comandas.data.section.SectionRepository
import javax.inject.Inject

class GetSectionsUseCase @Inject constructor(private val sectionRepository: SectionRepository) {
    suspend operator fun invoke() = sectionRepository.getSections()
}