package tfg.aperher.comandas.presentation.suggestions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.domain.model.Article
import tfg.aperher.comandas.domain.usecases.GetSuggestionsUseCase
import javax.inject.Inject

@HiltViewModel
class SuggestionsViewModel @Inject constructor(
    getSuggestionsUseCase: GetSuggestionsUseCase
) : ViewModel() {
    private val _popularDishes = MutableLiveData<List<Article>>()
    val popularDishes : LiveData<List<Article>> get() = _popularDishes

    private val _prominentDishes = MutableLiveData<List<Article>>()
    val prominentDishes : LiveData<List<Article>> get() = _prominentDishes

    init {
        viewModelScope.launch {
            val (popularDishes, prominentDishes) = getSuggestionsUseCase()

            popularDishes.fold(
                onSuccess = { _popularDishes.value = it },
                onFailure = { Log.e("SuggestionsViewModel", "Error getting popular dishes") }
            )

            prominentDishes.fold(
                onSuccess = { _prominentDishes.value = it },
                onFailure = { Log.e("SuggestionsViewModel", "Error getting prominent dishes") }
            )
        }
    }
}