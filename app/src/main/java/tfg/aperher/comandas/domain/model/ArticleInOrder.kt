package tfg.aperher.comandas.domain.model

data class ArticleInOrder(
    val id: List<String?> = listOf(null),
    val articleId: String,
    val name: String,
    val price: Double,
    val quantity: Int = id.size,
    val extras: List<Ingredient> = emptyList(),
    val state: State = State.PENDING
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArticleInOrder) return false

        return id.size == other.id.size && id.size == other.quantity && articleId == other.articleId && extras == other.extras
    }

    override fun hashCode(): Int {
        var result = id.size
        result = 31 * result + articleId.hashCode()
        result = 31 * result + extras.hashCode()
        return result
    }
}