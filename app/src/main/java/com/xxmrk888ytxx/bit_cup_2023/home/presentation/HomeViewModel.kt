package com.xxmrk888ytxx.bit_cup_2023.home.presentation

import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Category
import com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase.GetCategoriesUseCase
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
) : BaseViewModel() {
    private val searchBarText = MutableStateFlow("")
    private val categories = MutableStateFlow(emptyList<Category>())
    private val selectedCategoryId = MutableStateFlow<String?>(null)

    val screenState = combine(
        searchBarText,
        categories,
        selectedCategoryId
    ) { searchBarText, categories, selectedCategoryId ->
        HomeScreenState(searchBarText, categories, selectedCategoryId)
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
        }
    }

    init {
        loadCategory()
    }
}