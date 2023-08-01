package tfg.aperher.comandas.domain.model

data class Order(
    val id: String? = null,
    val tableId: String,
    val initTime: String? = null,
    val articles: List<ArticleInOrder> = emptyList()
) {
    fun isEmpty() = articles.isEmpty() && id == null

    fun areItemsSame(order: Order) =
        articles.size == order.articles.size && articles.zip(order.articles)
            .all { (a, b) -> a.id.size == b.id.size && a.articleId == b.articleId && a.extras == b.extras }
}