package tfg.aperher.comandas.domain.model

data class ArticleReady(
    val id: String,
    val orderId: String,
    val name: String,
    val section: String,
    val table: Int,
    val status: State,
    val extras: List<Ingredient> = emptyList()
)