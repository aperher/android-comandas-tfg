package tfg.aperher.comandas.presentation.ordertake.articleslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.usecases.GetArticleUseCase
import tfg.aperher.comandas.domain.model.Article
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(private val getArticleUseCase: GetArticleUseCase) :
    ViewModel() {

    private var _articles = MutableLiveData<List<Article>>()
    val articles: MutableLiveData<List<Article>> get() = _articles

    fun getArticles(categoryId: String) = viewModelScope.launch {
        getArticleUseCase(categoryId).fold(
            onSuccess = { _articles.value = it },
            onFailure = { }
        )
    }
}