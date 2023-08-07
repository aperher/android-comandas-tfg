package tfg.aperher.comandas.data.section

import retrofit2.Response
import tfg.aperher.comandas.data.section.model.SectionDto

interface SectionDataSource {
    suspend fun getSections(): Response<List<SectionDto>>
    suspend fun getSectionById(id: String): Response<SectionDto>
}