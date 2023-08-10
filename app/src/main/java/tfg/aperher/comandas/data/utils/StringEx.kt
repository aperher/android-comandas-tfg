package tfg.aperher.comandas.data.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.getDate(): String? =
    safeParseDateTimeWithFormatter(this, "dd/MM/yyyy")

fun String.getTime(): String? =
    safeParseDateTimeWithFormatter(this, "HH:mm")

private fun safeParseDateTimeWithFormatter(
    dateTime: String,
    pattern: String,
    formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
): String? {
    if (dateTime.isEmpty()) return null
    return try {
        val formatted = ZonedDateTime.parse(dateTime, formatter)
        val systemZone = ZoneId.systemDefault()
        formatted.withZoneSameInstant(systemZone).format(DateTimeFormatter.ofPattern(pattern))
    } catch (_: Exception) {
        null
    }
}