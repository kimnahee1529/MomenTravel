package com.android.traveltube.ui.datail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.traveltube.model.VideoDetailModel

class DetailViewModel(entity: VideoDetailModel) : ViewModel() {

    private val _uiState: MutableLiveData<DetailUiState> = MutableLiveData(DetailUiState.init())
    val uiState: LiveData<DetailUiState> get() = _uiState

    init {
        _uiState.value = uiState.value?.copy(
            videoId = entity.id,
            videoTitle = entity.title,
            videoDescription = entity.description,
            videoDate = entity.publishTime,

            )
    }
}

class DetailViewModelFactory(private val entity: VideoDetailModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(entity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}