package tfg.aperher.comandas.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.State
import javax.inject.Inject

class ChangeArticleQuantityUseCase @Inject constructor(
    private val addArticleOrderToListUseCase: AddArticleOrderToListUseCase
) {
    suspend operator fun invoke(
        list: List<ArticleInOrder>,
        index: Int,
        quantityChange: Int,
        defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    ): Pair<Int, List<ArticleInOrder>> = withContext(defaultDispatcher) {
        if (index < 0 || index >= list.size) return@withContext Pair(-1, list)

        if (quantityChange > 0) {
            addNewArticle(list, index)
        } else {
            removeArticle(list, index)
        }
    }

    private suspend fun addNewArticle(list: List<ArticleInOrder>, articleIndex: Int): Pair<Int, List<ArticleInOrder>> {
        val article = list[articleIndex]
        val newArticle = article.copy(
            //id = mutableListOf(null),
            quantity = 1,
            state = State.PENDING
        )
        return addArticleOrderToListUseCase(list, newArticle)
    }

    private fun removeArticle(list: List<ArticleInOrder>, articleIndex: Int): Pair<Int, List<ArticleInOrder>> {
        val articleInOrder = list[articleIndex]

        if (articleInOrder.state != State.PENDING) return Pair(-1, list)
        if (articleInOrder.quantity == 1) return Pair(-1, list.minus(articleInOrder))

        val updatedList = list.toMutableList()
        val replaceArticle = articleInOrder.copy(
            quantity = articleInOrder.quantity - 1,
            //id = articleInOrder.id.dropLast(1)
        )
        updatedList[articleIndex] = replaceArticle

        return Pair(articleIndex, updatedList)
    }
}