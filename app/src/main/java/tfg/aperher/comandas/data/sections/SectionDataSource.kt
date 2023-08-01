package tfg.aperher.comandas.data.sections

import retrofit2.Response
import tfg.aperher.comandas.data.sections.model.SectionDto

interface SectionDataSource {
    suspend fun getSections(): Response<List<SectionDto>>
    suspend fun getSectionById(id: String): Response<SectionDto>
}