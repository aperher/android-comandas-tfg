package tfg.aperher.comandas.domain.model

data class Section(val id: String, val name: String, val tables: List<Table> = emptyList())
