@file:Suppress("UNCHECKED_CAST")

package com.xxmrk888ytxx.bit_cup_2023.home.presentation

import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Category
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Image
import com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase.GetCategoriesUseCase
import com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase.GetCuratedImageUseCase
import com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase.GetInternetStateUseCase
import com.xxmrk888ytxx.bit_cup_2023.home.domain.useCase.SearchImageUseCase
import com.xxmrk888ytxx.bit_cup_2023.home.presentation.model.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCuratedImageUseCase: GetCuratedImageUseCase,
    private val getInternetStateUseCase: GetInternetStateUseCase,
    private val searchImageUseCase: SearchImageUseCase,
) : BaseViewModel() {
    private val searchBarText = MutableStateFlow("")
    private val categories = MutableStateFlow(emptyList<Category>())
    private val selectedCategoryId = MutableStateFlow<String?>(null)
    private val images = MutableStateFlow<List<Image>>(emptyList())
    private val isInternetError = MutableStateFlow(false)

    private val isLoadingCategories = MutableStateFlow(false)
    private val isLoadingCuratedImages = MutableStateFlow(false)
    private val isSearchImageInProcess = MutableStateFlow(false)
    private val isLoading = combine(
        isLoadingCategories,
        isLoadingCuratedImages,
        isSearchImageInProcess
    ) { isLoadingCategories, isLoadingCuratedImages, isSearchImageInProcess ->
        isLoadingCategories || isLoadingCuratedImages || isSearchImageInProcess
    }

    private val _toastAction = MutableSharedFlow<Int>(
        extraBufferCapacity = 1, replay = 1
    )
    val toastAction = _toastAction.asSharedFlow()

    val screenState = combine(
        searchBarText,
        categories,
        selectedCategoryId,
        images,
        isLoading,
        isInternetError
    ) { flowArray ->
        HomeScreenState(
            flowArray[0] as String,
            flowArray[1] as List<Category>,
            flowArray[2] as String?,
            flowArray[3] as List<Image>,
            flowArray[4] as Boolean,
            flowArray[5] as Boolean,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeScreenState())

    private val imageSearchLoadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

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
                .onSuccess { curatedImagesLoadResult ->
                    images.update { curatedImagesLoadResult.images }

                    if (curatedImagesLoadResult.isFromCache) {
                        onImagesLoadedFromCache()
                    }
                }
                .onFailure {
                    onInternetError()
                }

            isLoadingCuratedImages.update { false }
        }
    }

    fun onRetryLoadImage() {
        if (categories.value.isEmpty()) {
            loadCategory()
        }

        if (searchBarText.value.isEmpty()) {
            loadCuratedImages()
        } else {
            loadImagesBySearchQuery(searchBarText.value)
        }
    }

    @OptIn(FlowPreview::class)
    private fun collectSearchRequest() {
        viewModelScope.launch(Dispatchers.Default) {
            searchBarText
                .debounce(500)
                .collect {
                    if (it.isEmpty()) {
                        loadCuratedImages()
                    } else {
                        loadImagesBySearchQuery(it)
                    }
                }
        }
    }

    private fun loadImagesBySearchQuery(query: String) {
        imageSearchLoadScope.coroutineContext.cancelChildren()
        imageSearchLoadScope.launch(Dispatchers.IO) {
            isSearchImageInProcess.update { true }
            searchImageUseCase(query)
                .onSuccess { searchedImagesLocalResult ->
                    if (isActive) {
                        images.update { searchedImagesLocalResult.images }
                        if (searchedImagesLocalResult.isFromCache) {
                            onImagesLoadedFromCache()
                        }
                    }
                }
                .onFailure {
                    onInternetError()
                }
            if (isActive) {
                isSearchImageInProcess.update { false }
            }
        }
    }


    private fun collectImagesUpdates() {
        viewModelScope.launch(Dispatchers.Default) {
            images.collect { isInternetError.update { false } }
        }
    }

    private fun onInternetError() {
        isInternetError.update { true }
        _toastAction.tryEmit(R.string.no_internet_connection)
    }

    private suspend fun onImagesLoadedFromCache() {
        if (!getInternetStateUseCase().first()) {
            _toastAction.tryEmit(R.string.no_internet_connection_data_has_been_loaded_from_cache)
        }
    }

    override fun onCleared() {
        imageSearchLoadScope.cancel()
        super.onCleared()
    }

    init {
        loadCategory()
        loadCuratedImages()
        collectSearchRequest()
        collectImagesUpdates()
    }
}