package tfg.aperher.comandas.data.sections.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SectionDto(val id: String, val nombre: String, val mesas: List<TableDto> = emptyList()) {
    @JsonClass(generateAdapter = true)
    data class TableDto(val id: String, val numero: Int, val fecha_inicio : String?, val estado: String?)
}

