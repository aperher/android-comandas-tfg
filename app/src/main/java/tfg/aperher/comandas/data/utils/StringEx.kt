package tfg.aperher.comandas.data.utils

import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.getDate(): String {
    if (this.isEmpty()) return ""
    val dateTime = OffsetDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    return dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()
}

fun String.getTime(): String {
    if (this.isEmpty()) return ""
    val dateTime = ZonedDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    val systemZone = ZoneId.systemDefault()
    return dateTime.withZoneSameInstant(systemZone).toLocalTime()
        .format(DateTimeFormatter.ofPattern("HH:mm")).toString()
}