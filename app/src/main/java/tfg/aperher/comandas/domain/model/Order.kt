package tfg.aperher.comandas.domain.model

data class Order(
    val id: String? = null,
    val table: Int = -1,
    val section: String = "",
    val day: String = "",
    val initTime: String = "",
    val endTime: String = "",
    val articles: List<ArticleInOrder> = emptyList()
) {
    fun isEmpty() = articles.isEmpty() && id == null
}