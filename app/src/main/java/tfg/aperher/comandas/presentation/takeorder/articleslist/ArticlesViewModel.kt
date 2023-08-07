package tfg.aperher.comandas.presentation.takeorder.articleslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.usecases.GetArticlesUseCase
import tfg.aperher.comandas.domain.model.Article
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(private val getArticleUseCase: GetArticlesUseCase) :
    ViewModel() {

    private var _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    fun getArticles(categoryId: String) = viewModelScope.launch {
        getArticleUseCase(categoryId).fold(
            onSuccess = { _articles.value = it },
            onFailure = { }
        )
    }
}