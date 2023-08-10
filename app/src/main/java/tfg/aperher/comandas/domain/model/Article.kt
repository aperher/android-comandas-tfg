package tfg.aperher.comandas.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val image: String
) : Parcelable