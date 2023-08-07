package tfg.aperher.comandas.data.section.model

import tfg.aperher.comandas.data.utils.getTime
import tfg.aperher.comandas.domain.model.Section
import tfg.aperher.comandas.domain.model.State
import tfg.aperher.comandas.domain.model.Table

fun SectionDto.toDomain() = Section(id = id, name = nombre, tables = mesas.map { it.toDomain() })

private fun SectionDto.TableDto.toDomain() = Table(
    id = id,
    number = numero,
    orderId = comanda_id,
    initTime = fecha_inicio?.getTime(),
    state = State.values().find { it.value == estado } ?: State.AVAILABLE
)