package com.android.traveltube.ui.datail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.model.db.ChannelInfoModel
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.viewmodel.BasicDetailViewModel
import kotlinx.coroutines.launch

class DetailViewModel(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl,
    private val entity: VideoBasicModel
) : BasicDetailViewModel() {

    private val _uiState: MutableLiveData<DetailUiState> = MutableLiveData(DetailUiState.init())
    val uiState: LiveData<DetailUiState> get() = _uiState

    private val _uiChannelVideoState: MutableLiveData<List<VideoBasicModel>> = MutableLiveData()
    val uiChannelVideoState: LiveData<List<VideoBasicModel>> get() = _uiChannelVideoState

    init {
        _uiState.value = uiState.value?.copy(
            videoId = entity.id,
            videoThumbnail = entity.thumbNailUrl,
            videoTitle = entity.title,
            videoDescription = entity.description,
            videoDate = entity.publishTime,
            channelName = entity.channelTitle,
            channelThumbnail = entity.channelInfoModel?.channelThumbnail,
            subscriptionCount = entity.channelInfoModel?.subscriberCount,
            viewCount = entity.videoViewCountModel?.viewCount,
            isFavorite = entity.isFavorite

        )

        // 채널 동영상
        searchChannelVideos()
    }

    fun onClickedLike() {
        val currentFavoriteState = _uiState.value?.isFavorite ?: false
        val updatedFavoriteState = currentFavoriteState.not()

        _uiState.value = _uiState.value?.copy(isFavorite = updatedFavoriteState)

        updateFavoriteStatus(entity.id, updatedFavoriteState)
    }

    fun onVideoPlaying() {
        if (entity.isSaved || entity.modelType == ModelType.VIDEO_CATEGORY_SHORTS) {
            return
        }
        viewModelScope.launch {
            youtubeRepositoryImpl.updateSavedStatus(entity.id, true)
        }
    }

    private fun updateFavoriteStatus(videoId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            youtubeRepositoryImpl.updateFavoriteStatus(videoId, isFavorite)
        }
    }

    private fun searchChannelVideos() = viewModelScope.launch {
        if (entity.channelId == null) {
            return@launch
        }

        val videos = youtubeRepositoryImpl.getChannelVideos(entity.channelId)
        val videoItemModels = videos.items
            .mapNotNull { item ->
                val channelInfoList = getChannelInfo(item.snippet.channelId, youtubeRepositoryImpl)
                val videoViewCountList =
                    (item.id.videoId ?: item.id.kind)?.let {
                        getVideoViewCount(
                            it,
                            youtubeRepositoryImpl
                        )
                    }
                val videoViewCountModel = videoViewCountList?.firstOrNull()

                val videoId = item.id.videoId ?: item.id.kind

                if (videoId != null) {
                    val channelInfoModel = if (channelInfoList.isNotEmpty()) {
                        channelInfoList.first()
                    } else {
                        ChannelInfoModel(
                            channelId = null,
                            channelThumbnail = null,
                            subscriberCount = null,
                            hiddenSubscriberCount = null
                        )
                    }

                    VideoBasicModel(
                        id = videoId,
                        thumbNailUrl = item.snippet.thumbnails.medium.url,
                        channelId = item.snippet.channelId,
                        channelTitle = item.snippet.channelTitle,
                        title = item.snippet.title,
                        description = item.snippet.description,
                        publishTime = item.snippet.publishedAt,
                        channelInfoModel = channelInfoModel,
                        videoViewCountModel = videoViewCountModel,
                        modelType = ModelType.VIDEO_CHANNEL
                    )
                } else {
                    null
                }
            }



        _uiChannelVideoState.postValue(videoItemModels)
    }

}

class DetailViewModelFactory(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl,
    private val entity: VideoBasicModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(youtubeRepositoryImpl, entity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}