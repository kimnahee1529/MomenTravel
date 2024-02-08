package com.android.traveltube.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.traveltube.data.search.Item
import com.android.traveltube.model.ChannelInfoModel
import com.android.traveltube.model.VideoDetailModel
import com.android.traveltube.repository.YoutubeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel(
    private val youtubeRepository: YoutubeRepository
) : ViewModel() {
    private val _detailItems = MutableLiveData<List<VideoDetailModel>>()
    val detailItems: LiveData<List<VideoDetailModel>> get() = _detailItems

    //영상 검색 정보 가져오는 getSearchingVideos 호출
    fun getDetailItem(): LiveData<List<VideoDetailModel>> {
        loadSearchingVideos()
        return detailItems
    }

//    private fun loadSearchingVideos() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                val videos = youtubeRepository.getSearchingVideos()
//                val videoItemModels = videos.items.map { item ->
//                    convertToSearchItemModel(item)
//                }
//                _detailItems.postValue(videoItemModels)
//                Log.d("sharedviewmodel search", videoItemModels.toString())
//            }.onFailure { exception ->
//                withContext(Dispatchers.Main) {
//                    Log.e(
//                        "sharedviewmodel detailItem search Error",
//                        "Failed to fetch trending videos",
//                        exception
//                    )
//                }
//            }
//        }
//    }

    private fun loadSearchingVideos() {
        viewModelScope.launch {
            kotlin.runCatching {
                val videos = youtubeRepository.getSearchingVideos()
                val videoItemModels = videos.items.map { item ->
                    val channelInfoList = getChannelInfo(item.snippet.channelId)

                    run {
                        val channelInfo = channelInfoList.first()

                        VideoDetailModel(
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

                _detailItems.postValue(videoItemModels)
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

    //영상 검색 정보 가져오는 getSearchingVideos 호출
    fun getChannelItem(): LiveData<List<VideoDetailModel>> {
        loadChannelsVideos()
        return detailItems
    }

    private fun loadChannelsVideos() {
        viewModelScope.launch {
            kotlin.runCatching {
                val channel = youtubeRepository.getChannelsVideo()
                val videoItemModels = channel.items.map { item ->
                    convertToSearchItemModel(item)
                }
                _detailItems.postValue(videoItemModels)
                Log.d("sharedviewmodel channel", videoItemModels.toString())
            }.onFailure { exception ->
                withContext(Dispatchers.Main) {
                    Log.e(
                        "sharedviewmodel channel Error",
                        "Failed to fetch getting channel",
                        exception
                    )
                }
            }
        }
    }


    //Item 데이터 타입을 Detail 화면에서 쓰기 좋게 VideoDetailModel로 변환
    private fun convertToSearchItemModel(item: Item): VideoDetailModel {
//           TODO : 추가할 수 있으면 viewCount도 추가하기
        return VideoDetailModel(
            id = item.id.videoId,
            thumbNailUrl = item.snippet.thumbnails.medium.url,
            channelId = item.snippet.channelId,
            channelTitle = item.snippet.channelTitle,
            title = item.snippet.title,
            description = item.snippet.description,
            publishTime = item.snippet.publishedAt,
        )
    }

    private fun convertToChannelInfoModel(item: com.android.traveltube.data.channel.Item): ChannelInfoModel {
        return ChannelInfoModel(
            channelId = item.id,
            channelThumbnail = item.snippet.thumbnails.default.url,
            subscriberCount = item.statistics.subscriberCount,
            hiddenSubscriberCount = item.statistics.hiddenSubscriberCount
        )
    }

    private suspend fun getChannelInfo(channelId: String): List<ChannelInfoModel> {
        return try {
            val channel = youtubeRepository.getChannelInfo(channelId = channelId)
            channel.items.map { item ->
                convertToChannelInfoModel(item)
            }
        } catch (exception: Exception) {
            Log.e(
                "sharedviewmodel channel Error",
                "Failed to fetch getting channel",
                exception
            )
            emptyList()
        }
    }

}