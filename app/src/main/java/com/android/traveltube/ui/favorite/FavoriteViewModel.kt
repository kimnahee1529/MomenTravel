package com.android.traveltube.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.traveltube.R
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.repository.YoutubeRepositoryImpl
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl,
) : ViewModel() {
    private val _isEditMode = MutableLiveData(EditModeUiState.init())
    val isEditMode: LiveData<EditModeUiState> get() = _isEditMode

    private val _favoriteVideos = MutableLiveData<List<VideoBasicModel>>()
    val favoriteVideos: LiveData<List<VideoBasicModel>> get() = _favoriteVideos

    private val _selectedSortingOption = MutableLiveData(SortingOption.init())
    val selectedSortingOption: LiveData<SortingOption> get() = _selectedSortingOption


    fun sortingFavoriteVideos(position: Int) = viewModelScope.launch {
        val unsortedList = youtubeRepositoryImpl.getFavoriteVideos()
        val sortedList = when (position) {
            0 -> unsortedList
            1 -> unsortedList.sortedBy { it.title }
            2 -> unsortedList.sortedByDescending { it.publishTime?.time }
            else -> unsortedList
        }

        _favoriteVideos.value = sortedList
    }

    fun toggleEditMode() {
        val currentEditMode = _isEditMode.value ?: EditModeUiState.init()
        val newEditMode = currentEditMode.copy(
            isEditMode = currentEditMode.isEditMode.not(),
            buttonText = if (currentEditMode.isEditMode) R.string.edit else R.string.complete
        )
        _isEditMode.value = newEditMode
    }

    fun deleteFavoriteItem(videoId: String) {
        viewModelScope.launch {
            youtubeRepositoryImpl.updateFavoriteStatus(videoId, false)
            sortingFavoriteVideos(selectedSortingOption.value?.position ?: 0)
        }
    }

    fun setSelectedSortingOption(sortingOption: SortingOption) {
        _selectedSortingOption.value = sortingOption
    }

}

class FavoriteViewModelFactory(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(youtubeRepositoryImpl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}