package tfg.aperher.comandas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Table(val id: String, val number: Int, var dateTime: String?, val state: State) : Parcelable