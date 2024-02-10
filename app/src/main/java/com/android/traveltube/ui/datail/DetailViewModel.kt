package com.android.traveltube.ui.datail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.traveltube.data.videos.Item
import com.android.traveltube.model.ChannelInfoModel
import com.android.traveltube.model.VideoViewCountModel
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.repository.YoutubeRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val youtubeRepository: YoutubeRepository,
    private val entity: VideoRecommendModel
) : ViewModel() {

    private val _uiState: MutableLiveData<DetailUiState> = MutableLiveData(DetailUiState.init())
    val uiState: LiveData<DetailUiState> get() = _uiState

    private val _uiRecommendState: MutableLiveData<List<VideoRecommendModel>> = MutableLiveData()
    val uiRecommendState: LiveData<List<VideoRecommendModel>> get() = _uiRecommendState

    private val _uiChannelVideoState: MutableLiveData<List<VideoRecommendModel>> = MutableLiveData()
    val uiChannelVideoState: LiveData<List<VideoRecommendModel>> get() = _uiChannelVideoState

    init {
        _uiState.value = uiState.value?.copy(
            videoId = entity.id,
            videoTitle = entity.title,
            videoDescription = entity.description,
            videoDate = entity.publishTime,
            channelName = entity.channelTitle,
            channelThumbnail = entity.channelInfoModel?.channelThumbnail,
            subscriptionCount = entity.channelInfoModel?.subscriberCount,
            viewCount = entity.videoViewCountModel?.viewCount
        )

        searchChannelVideos()
    }

    fun onClickedLike() {
        val currentFavoriteState = _uiState.value?.isFavorite ?: false
        val updatedFavoriteState = currentFavoriteState.not()

        _uiState.value = _uiState.value?.copy(isFavorite = updatedFavoriteState)
    }

    fun getRecommendVideos() = viewModelScope.launch {
        try {
            val allRecommendVideos = youtubeRepository.getRecommendVideos()
            _uiRecommendState.value = allRecommendVideos
        } catch (e: Exception) {
            Log.e("DetailViewModel", "Error getting recommend videos: ${e.message}")
        }
    }

    private fun searchChannelVideos() = viewModelScope.launch {
        if (entity.channelId == null) {
            return@launch
        }
        val videos = youtubeRepository.getChannelVideos(entity.channelId)
        val videoItemModels = videos.items.map { item ->
            val channelInfoList = getChannelInfo(item.snippet.channelId)
            val videoViewCountList = getVideoViewCount(item.id.videoId)
            val videoViewCountModel = videoViewCountList.firstOrNull()

            VideoRecommendModel(
                id = item.id.videoId,
                thumbNailUrl = item.snippet.thumbnails.medium.url,
                channelId = item.snippet.channelId,
                channelTitle = item.snippet.channelTitle,
                title = item.snippet.title,
                description = item.snippet.description,
                publishTime = item.snippet.publishedAt,
                channelInfoModel = channelInfoList.first(),
                videoViewCountModel = videoViewCountModel
            )
        }
        _uiChannelVideoState.postValue(videoItemModels)
    }

    private suspend fun getChannelInfo(channelId: String): List<ChannelInfoModel> {
        return try {
            val channel = youtubeRepository.getChannelInfo(channelId = channelId)
            channel.items.map { item ->
                convertToChannelInfoModel(item)
            }
        } catch (exception: Exception) {
            emptyList()
        }
    }

    private suspend fun getVideoViewCount(videoId: String): List<VideoViewCountModel> {
        return try {
            val count = youtubeRepository.getViewCount(videoId = videoId)
            count.items.map { item ->
                convertToViewCountModel(item)
            }
        } catch (exception: Exception) {
            emptyList()
        }
    }

    private fun convertToChannelInfoModel(item: com.android.traveltube.data.channel.Item): ChannelInfoModel {
        return ChannelInfoModel(
            channelId = item.id,
            channelThumbnail = item.snippet.thumbnails.default.url,
            subscriberCount = item.statistics.subscriberCount,
            hiddenSubscriberCount = item.statistics.hiddenSubscriberCount
        )
    }

    private fun convertToViewCountModel(item: Item): VideoViewCountModel {
        return VideoViewCountModel(
            videoId = item.id,
            viewCount = item.statistics.viewCount,
            commentCount = item.statistics.commentCount,
            likeCount = item.statistics.likeCount
        )
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