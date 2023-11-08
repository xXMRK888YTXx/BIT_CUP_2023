@file:Suppress("UNCHECKED_CAST")

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
    private val isInternetError = MutableStateFlow(false)

    private val isLoadingCategories = MutableStateFlow(false)
    private val isLoadingCuratedImages = MutableStateFlow(false)
    private val isLoading = combine(
        isLoadingCategories,
        isLoadingCuratedImages
    ) { isLoadingCategories, isLoadingCuratedImages ->
        isLoadingCategories || isLoadingCuratedImages
    }

    val screenState = combine(
        searchBarText,
        categories,
        selectedCategoryId,
        images,
        isLoading,
        isInternetError
    ) { array ->
        HomeScreenState(
            array[0] as String,
            array[1] as List<Category>,
            array[2] as String?,
            array[3] as List<Image>,
            array[4] as Boolean,
            array[5] as Boolean,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeScreenState())

    fun onSearchTextChanged(text: String) {
        searchBarText.update { text }
    }

    private fun loadCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingCategories.update { true }
            getCategoriesUseCase()
                .onSuccess { categoriesList ->
                    categories.update { categoriesList }
                    selectedCategoryId.update { categoriesList.firstOrNull()?.id }
                }
            isLoadingCategories.update { false }
        }
    }

    fun loadCuratedImages() {
        isLoadingCuratedImages.update { true }
        viewModelScope.launch(Dispatchers.IO) {
            getCuratedImageUseCase()
                .onSuccess { curatedImages ->
                    images.update { curatedImages }
                }
                .onFailure { isInternetError.update { true } }

            isLoadingCuratedImages.update { false }
        }
    }

    fun onRetryLoadImage() {
        if(categories.value.isEmpty()) {
            loadCategory()
        }
        loadCuratedImages()
    }

    init {
        loadCategory()
        loadCuratedImages()
    }
}