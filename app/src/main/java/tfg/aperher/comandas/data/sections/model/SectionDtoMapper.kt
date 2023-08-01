package tfg.aperher.comandas.data.sections.model

import tfg.aperher.comandas.domain.model.Section
import tfg.aperher.comandas.domain.model.State
import tfg.aperher.comandas.domain.model.Table

fun SectionDto.toDomain() = Section(id = id, name = nombre, tables = mesas.map { it.toDomain() })

private fun SectionDto.TableDto.toDomain() = Table(
    id = id,
    number = numero,
    dateTime = fecha_inicio,
    state = State.values().find { it.value == estado } ?: State.AVAILABLE
)