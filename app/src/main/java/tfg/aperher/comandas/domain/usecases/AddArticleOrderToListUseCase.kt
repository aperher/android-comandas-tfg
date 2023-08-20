package tfg.aperher.comandas.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.State
import javax.inject.Inject

class AddArticleOrderToListUseCase @Inject constructor() {
    suspend operator fun invoke(
        list: List<ArticleInOrder>,
        articleInOrder: ArticleInOrder,
        defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    ): Pair<Int, List<ArticleInOrder>> = withContext(defaultDispatcher) {
        val repeatedArticleIndex = getRepeatedArticleIndexInList(list, articleInOrder)

        val newList = if (repeatedArticleIndex != -1) {
            updateRepeatedArticle(list, repeatedArticleIndex, articleInOrder)
        } else {
            list.plus(articleInOrder)
        }

        Pair(repeatedArticleIndex, newList)
    }

    private fun getRepeatedArticleIndexInList(list: List<ArticleInOrder>, article: ArticleInOrder) =
        list.indexOfFirst {
            it.articleId == article.articleId && it.extras == article.extras && it.state == State.PENDING
        }

    private fun updateRepeatedArticle(list: List<ArticleInOrder>, index: Int, article: ArticleInOrder) : List<ArticleInOrder> {
        val updatedList = list.toMutableList()
        val repeatedArticle = updatedList[index]

        val replaceArticle = repeatedArticle.copy(
            quantity = repeatedArticle.quantity + article.quantity,
            //id = repeatedArticle.id.plus(article.id)
        )
        updatedList[index] = replaceArticle

        return updatedList.toList()
    }
}
