package tfg.aperher.comandas.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.data.sections.SectionRepository
import tfg.aperher.comandas.domain.model.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetTablesDetailsUseCase @Inject constructor(
    private val sectionRepository: SectionRepository
) {
    suspend operator fun invoke(sectionId: String): Result<List<Table>> {
        val sectionResult = sectionRepository.getSectionById(sectionId)

        return withContext(Dispatchers.Default) {
            sectionResult.map { section ->
                section.tables.map { table ->
                    table.dateTime = toTimeFormat(table.dateTime)
                    table
                }
            }
        }
    }

    private fun toTimeFormat(dateTime: String?) = dateTime?.let {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val parsedDateTime = LocalDateTime.parse(it, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        parsedDateTime.format(formatter)
    }
}