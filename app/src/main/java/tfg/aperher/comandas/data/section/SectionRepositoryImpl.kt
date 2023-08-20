package tfg.aperher.comandas.data.section


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.section.model.toDomain
import tfg.aperher.comandas.data.utils.toResult
import tfg.aperher.comandas.domain.model.Section
import javax.inject.Inject

class SectionRepositoryImpl @Inject constructor(private val datasource: SectionDataSource) :
    SectionRepository {
    override suspend fun getSections(): Result<List<Section>> = withContext(Dispatchers.IO) {
        datasource.getSections().toResult { listDto ->
            listDto.map { it.toDomain() }
        }
    }

    override suspend fun getSectionById(id: String): Result<Section> = withContext(Dispatchers.IO) {
        datasource.getSectionById(id).toResult { it.toDomain() }
    }
}