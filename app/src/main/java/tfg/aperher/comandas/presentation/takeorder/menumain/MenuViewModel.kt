package tfg.aperher.comandas.presentation.takeorder.menumain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tfg.aperher.comandas.data.category.CategoryRepository
import tfg.aperher.comandas.domain.model.Category
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(categoryRepository: CategoryRepository): ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    init {
        viewModelScope.launch {
            categoryRepository.getCategories().fold(
                onSuccess = { _categories.value = it },
                onFailure = { })
        }
    }
}