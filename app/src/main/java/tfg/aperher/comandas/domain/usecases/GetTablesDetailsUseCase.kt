package tfg.aperher.comandas.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import tfg.aperher.comandas.data.realtime.RealtimeRepository
import tfg.aperher.comandas.data.section.SectionRepository
import tfg.aperher.comandas.domain.model.Table
import javax.inject.Inject

class GetTablesDetailsUseCase @Inject constructor(
    private val sectionRepository: SectionRepository,
    private val realtimeRepository: RealtimeRepository
) {
    operator fun invoke(sectionId: String): Flow<List<Table>> = flow {
        val sectionResult = sectionRepository.getSectionById(sectionId).getOrNull()
        val sectionTables = sectionResult?.tables?.toMutableList() ?: mutableListOf()

        emit(sectionTables)

        realtimeRepository.listeningForUpdatedOrders(sectionId)
            .collect { (order, state) ->
                sectionTables.indexOfFirst { it.id == order.tableId }.takeIf { it != -1 }?.let {
                    sectionTables[it] = sectionTables[it].copy(
                        orderId = order.id,
                        state = state,
                        initTime = order.initTime
                    )
                }

                emit(sectionTables)
            }
    }.flowOn(Dispatchers.IO)
}