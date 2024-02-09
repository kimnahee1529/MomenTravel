package com.android.traveltube.ui.datail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.repository.YoutubeRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val youtubeRepository: YoutubeRepository,
    entity: VideoRecommendModel
) : ViewModel() {

    private val _uiState: MutableLiveData<DetailUiState> = MutableLiveData(DetailUiState.init())
    val uiState: LiveData<DetailUiState> get() = _uiState

    private val _uiRecommendState: MutableLiveData<List<VideoRecommendModel>> = MutableLiveData()
    val uiRecommendState: LiveData<List<VideoRecommendModel>> get() = _uiRecommendState

    init {
        _uiState.value = uiState.value?.copy(
            videoId = entity.id,
            videoTitle = entity.title,
            videoDescription = entity.description,
            videoDate = entity.publishTime,
            channelName = entity.channelTitle,
            channelThumbnail = entity.channelInfoModel?.channelThumbnail,
            subscriptionCount = entity.channelInfoModel?.subscriberCount

        )
    }

    fun onClickedLike() {
        val currentFavoriteState = _uiState.value?.isFavorite ?: false
        val updatedFavoriteState = currentFavoriteState.not()

        _uiState.value = _uiState.value?.copy(isFavorite = updatedFavoriteState)
    }

    fun getRecommendVideos() {
        viewModelScope.launch {
            try {
                val allRecommendVideos = youtubeRepository.getRecommendVideos()
                _uiRecommendState.value = allRecommendVideos
            } catch (e: Exception) {
                Log.d("TAG", e.toString())
            }
        }
    }
}

class DetailViewModelFactory(
    private val youtubeRepository: YoutubeRepository,
    private val entity: VideoRecommendModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(youtubeRepository, entity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}