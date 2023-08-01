package tfg.aperher.comandas.domain.model

data class ArticleInOrder(
    val id: List<String?> = listOf(null),
    val articleId: String,
    val name: String,
    val price: Double,
    val quantity: Int = id.size,
    val extras: List<Ingredient> = emptyList(),
    val state: State = State.PENDING
)