package com.xxmrk888ytxx.bit_cup_2023.home.presentation

import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Category
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Image
import com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase.GetCategoriesUseCase
import com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase.GetCuratedImageUseCase
import com.xxmrk888ytxx.bit_cup_2023.home.presentation.model.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCuratedImageUseCase: GetCuratedImageUseCase,
) : BaseViewModel() {
    private val searchBarText = MutableStateFlow("")
    private val categories = MutableStateFlow(emptyList<Category>())
    private val selectedCategoryId = MutableStateFlow<String?>(null)
    private val images = MutableStateFlow<List<Image>>(emptyList())

    val screenState = combine(
        searchBarText,
        categories,
        selectedCategoryId,
        images
    ) { searchBarText, categories, selectedCategoryId, images ->
        HomeScreenState(searchBarText, categories, selectedCategoryId, images)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeScreenState())

    fun onSearchTextChanged(text: String) {
        searchBarText.update { text }
    }

    private fun loadCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase()
                .onSuccess { categoriesList ->
                    categories.update { categoriesList }
                    selectedCategoryId.update { categoriesList.firstOrNull()?.id }
                }
                .onFailure { it.message }
        }
    }

    private fun loadCuratedImages() {
        viewModelScope.launch(Dispatchers.IO) {
            getCuratedImageUseCase()
                .onSuccess { curatedImages ->
                    images.update { curatedImages }
                }
                .onFailure { it.message }
        }
    }

    init {
        loadCategory()
        loadCuratedImages()
    }
}