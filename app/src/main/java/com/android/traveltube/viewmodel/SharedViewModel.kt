package com.android.traveltube.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.traveltube.model.VideoDetailModel
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.repository.YoutubeRepository

class SharedViewModel(
    private val youtubeRepository: YoutubeRepository
) : ViewModel() {
    private val _searchResults = MutableLiveData<List<VideoRecommendModel>>()
    val searchResults: LiveData<List<VideoRecommendModel>> get() = _searchResults
    private val _searchTravelResults = MutableLiveData<List<VideoRecommendModel>>()
    val searchTravelResults: LiveData<List<VideoRecommendModel>> get() = _searchTravelResults

    private val _detailItems = MutableLiveData<List<VideoDetailModel>>()
    val detailItems: LiveData<List<VideoDetailModel>> get() = _detailItems

    fun getResultsVideoList(list: List<VideoRecommendModel>) {
        _searchResults.value = list
    }
    fun getResultsTravelLVideoList(list: List<VideoRecommendModel>) {
        _searchTravelResults.value = list
    }
}











// Item 데이터 타입을 Detail 화면에서 쓰기 좋게 VideoDetailModel로 변환
//fun convertToSearchItemModel(item: Item): VideoDetailModel {
//    return VideoDetailModel(
//        id = item.id.videoId,
//        thumbNailUrl = item.snippet.thumbnails.medium.url,
//        channelId = item.snippet.channelId,
//        channelTitle = item.snippet.channelTitle,
//        title = item.snippet.title,
//        description = item.snippet.description,
//        publishTime = item.snippet.publishedAt,
//    )
//}

//영상 Id로 영상 조회수를 가져오는 getSearchingVideos 호출
//fun getVideoViewCount(): LiveData<List<VideoDetailModel>> {
//        loadVideoViewCount()
//    return detailItems
//}

//    fun loadVideoViewCount() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                val channel = youtubeRepository.getViewCount()
//                val videoItemModels = channel.items.map { item ->
//                    Log.d("홈 화면 영상 조회수", "count: ${item.statistics.viewCount}")
//                    //                    convertToSearchItemModel(item)
//                }
////                _detailItems.postValue(videoItemModels)
////                Log.d("sharedviewmodel channel", videoItemModels.toString())
//            }.onFailure { exception ->
//                withContext(Dispatchers.Main) {
//                    Log.e(
//                        "sharedviewmodel viewcount Error",
//                        "Failed to fetch getting channel",
//                        exception
//                    )
//                }
//            }
//        }
//    }

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
//}

//        private fun loadSearchingVideos() {
//            viewModelScope.launch {
//                kotlin.runCatching {
//                    val videos = youtubeRepository.getSearchingVideos()
//                    val videoItemModels = videos.items.map { item ->
//                        val channelInfoList = getChannelInfo(item.snippet.channelId)
//
//                        run {
//                            val channelInfo = channelInfoList.first()
//
//                            VideoDetailModel(
//                                id = item.id.videoId,
//                                thumbNailUrl = item.snippet.thumbnails.medium.url,
//                                channelId = item.snippet.channelId,
//                                channelTitle = item.snippet.channelTitle,
//                                title = item.snippet.title,
//                                description = item.snippet.description,
//                                publishTime = item.snippet.publishedAt,
//                                channelInfoModel = channelInfo
//                            )
//                            Log.d("홈 화면 영상 썸네일", item.snippet.thumbnails.medium.url)
//                            Log.d("홈 화면 채널 썸네일", channelInfo.channelThumbnail.toString())
//                            Log.d("홈 화면 영상 제목", item.snippet.description)
//                            Log.d("홈 화면 채널 이름", item.snippet.channelTitle)
//                            Log.d("홈 화면 영상 게시일", item.snippet.publishedAt.toString())
//                        }
//                    }
//                    _detailItems.postValue(videoItemModels)
//                    Log.d("sharedviewmodel search", videoItemModels.toString())
//                }.onFailure { exception ->
//                    withContext(Dispatchers.Main) {
//                        Log.e(
//                            "sharedviewmodel detailItem search Error",
//                            "Failed to fetch trending videos",
//                            exception
//                        )
//                    }
//                }
//            }
//        }

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

//    //영상 검색 정보 가져오는 getSearchingVideos 호출
//    fun getChannelItem(): LiveData<List<VideoDetailModel>> {
//        loadChannelsVideos()
//        return detailItems
//    }
//
//    private fun loadChannelsVideos() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                val channel = youtubeRepository.getChannelsVideo()
//                val videoItemModels = channel.items.map { item ->
//                    convertToSearchItemModel(item)
//                }
//                _detailItems.postValue(videoItemModels)
//                Log.d("sharedviewmodel channel", videoItemModels.toString())
//            }.onFailure { exception ->
//                withContext(Dispatchers.Main) {
//                    Log.e(
//                        "sharedviewmodel channel Error",
//                        "Failed to fetch getting channel",
//                        exception
//                    )
//                }
//            }
//        }
//    }