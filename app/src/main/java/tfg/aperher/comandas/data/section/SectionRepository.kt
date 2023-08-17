package tfg.aperher.comandas.data.section

import tfg.aperher.comandas.domain.model.Section

interface SectionRepository {
    suspend fun getSections(): Result<List<Section>>
    suspend fun getSectionById(id: String): Result<Section>
}