package tfg.aperher.comandas.presentation.orders.readyarticles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.usecases.GetReadyArticlesUseCase
import tfg.aperher.comandas.domain.usecases.SetServedArticleUseCase
import javax.inject.Inject

@HiltViewModel
class ReadyArticlesViewModel @Inject constructor(
    getReadyArticlesUseCase: GetReadyArticlesUseCase,
    private val setServedArticleUseCase: SetServedArticleUseCase
) : ViewModel() {

    val readyArticles = getReadyArticlesUseCase().asLiveData()

    fun setServedArticleAtPosition(adapterPosition: Int) {
        viewModelScope.launch {
            val readyArticleId = readyArticles.value!![adapterPosition].id
            setServedArticleUseCase(readyArticleId)
        }
    }
}