package com.android.traveltube.ui.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.traveltube.model.ChannelInfoModel
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.repository.YoutubeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailCityViewModel(
    private val youtubeRepository: YoutubeRepository
) : ViewModel() {
    private val _searchResults = MutableLiveData<List<VideoRecommendModel>>()
    val searchResults: LiveData<List<VideoRecommendModel>> get() = _searchResults

    fun getSearchVideoList() {
        searchVideoList()
    }

    //영상 검색 정보 가져오는 getSearchingVideos 호출
    private fun searchVideoList() {
        viewModelScope.launch {
            kotlin.runCatching {
                val videos = youtubeRepository.getSearchingVideos()
                val videoItemModels = videos.items.map { item ->
                    val channelInfoList = getChannelInfo(item.snippet.channelId)

                    run {
                        val channelInfo = channelInfoList.first()

                        VideoRecommendModel(
                            id = item.id.videoId,
                            thumbNailUrl = item.snippet.thumbnails.medium.url,
                            channelId = item.snippet.channelId,
                            channelTitle = item.snippet.channelTitle,
                            title = item.snippet.title,
                            description = item.snippet.description,
                            publishTime = item.snippet.publishedAt,
                            channelInfoModel = channelInfo
                        )
                    }
                }

                _searchResults.postValue(videoItemModels)
                saveSearchResult(videoItemModels)
                Log.d("sharedviewmodel search", videoItemModels.toString())
            }.onFailure { exception ->
                withContext(Dispatchers.Main) {
                    Log.e(
                        "sharedviewmodel detailItem search Error",
                        "Failed to fetch trending videos",
                        exception
                    )
                }
            }
        }
    }

    private fun saveSearchResult(results: List<VideoRecommendModel>) = viewModelScope.launch {
        youtubeRepository.insertRecommendVideo(results)
    }

    private suspend fun getChannelInfo(channelId: String): List<ChannelInfoModel> {
        return try {
            val channel = youtubeRepository.getChannelInfo(channelId = channelId)
            channel.items.map { item ->
                convertToChannelInfoModel(item)
            }
        } catch (exception: Exception) {
            Log.e("sharedviewmodel channel Error", "Failed to fetch getting channel", exception)
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
}

class DetailCityViewModelProviderFactory(
    private val youtubeRepository: YoutubeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailCityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailCityViewModel(youtubeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}