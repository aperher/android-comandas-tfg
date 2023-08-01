package tfg.aperher.comandas.presentation.ordertake.articledetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import tfg.aperher.comandas.domain.model.Article
import tfg.aperher.comandas.domain.model.ArticleInOrder
import tfg.aperher.comandas.domain.model.State
import tfg.aperher.comandas.utils.Event
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor() : ViewModel() {

    private val _article = MutableLiveData<Article>()
    val article: MutableLiveData<Article> get() = _article

    private val _quantity = MutableLiveData(1)
    val quantity: MutableLiveData<Int> get() = _quantity

    val isRemoveButtonEnabled = quantity.map { it > 1 }
    val isAddButtonEnabled = quantity.map { it < 15 }

    private val _observations = MutableLiveData<String>()
    val observations: MutableLiveData<String> get() = _observations

    private val _addArticleToOrder = MutableLiveData<Event<ArticleInOrder>>()
    val addArticleToOrder get() = _addArticleToOrder

    fun setArticle(article: Article) {
        _article.value = article
    }

    fun addArticle() {
        _quantity.value = _quantity.value?.plus(1)
    }

    fun removeArticle() {
        _quantity.value = _quantity.value?.minus(1)
    }

    fun setObservations(inputText: String) {
        _observations.value = inputText
    }

    fun addArticleToOrder() {
        val article = article.value ?: return
        val articleInOrder = ArticleInOrder(
            MutableList(_quantity.value ?: 1) { null },
            article.id,
            article.name,
            article.price,
            _quantity.value ?: 1,
            emptyList(),
            State.PENDING
        )
        _addArticleToOrder.value = Event(articleInOrder)
    }
}
