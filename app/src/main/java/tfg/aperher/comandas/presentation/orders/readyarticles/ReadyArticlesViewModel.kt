package tfg.aperher.comandas.presentation.orders.readyarticles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.model.ArticleReady
import tfg.aperher.comandas.domain.usecases.GetReadyArticlesUseCase
import tfg.aperher.comandas.domain.usecases.SetServedArticleUseCase
import javax.inject.Inject

@HiltViewModel
class ReadyArticlesViewModel @Inject constructor(
    getReadyArticlesUseCase: GetReadyArticlesUseCase,
    private val setServedArticleUseCase: SetServedArticleUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getReadyArticlesUseCase().collect { readyArticles ->
                readyArticlesStateFlow.value = readyArticles
            }
        }
    }

    private val readyArticlesStateFlow: MutableStateFlow<List<ArticleReady>> = MutableStateFlow(emptyList())
    val readyArticles: StateFlow<List<ArticleReady>> = readyArticlesStateFlow

    fun setServedArticleAtPosition(adapterPosition: Int) {
        viewModelScope.launch {
            val readyArticleId = readyArticles.value[adapterPosition].id
            setServedArticleUseCase(readyArticleId)
        }
    }
}