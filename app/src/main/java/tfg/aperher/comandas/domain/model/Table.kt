package tfg.aperher.comandas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Table(
    val id: String,
    val number: Int,
    val orderId: String?,
    val initTime: String?,
    val state: State
) : Parcelable