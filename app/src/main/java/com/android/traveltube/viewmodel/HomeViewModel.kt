package com.android.traveltube.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.traveltube.data.search.Item as SearchItem
import com.android.traveltube.factory.PreferencesRepository
import com.android.traveltube.model.VideoDetailModel
import com.android.traveltube.repository.YoutubeRepositoryImpl

class HomeViewModel(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    private val _videoData = MutableLiveData<List<VideoDetailModel>>()
    val videoData: LiveData<List<VideoDetailModel>> = _videoData

    //아직은 아래의 코드(video:list)를 쓸 일이 없음!
//    fun loadTrendingVideos(region: String = "KR"){
//        viewModelScope.launch {
//            kotlin.runCatching {
//                val videos = youtubeRepository.getTrendingVideos(region)
//                Log.d("viewmodel videos", videos.toString())
//            }.onFailure { exception ->
//                withContext(Dispatchers.Main) {
//                    Log.e("viewmodel videos Error", "Failed to fetch trending videos", exception)
//                }
//            }
//        }
//    }

//    fun loadSearchingVideos(region: String = "KR"){
//        viewModelScope.launch {
//            kotlin.runCatching {
//                val videos = youtubeRepository.getSearchingVideos(region)
//                val videoItemModels = videos.items.map { item ->
//                    convertToSearchItemModel(item)
//                }
//                _videoData.postValue(videoItemModels)
//                Log.d("viewmodel search", videos.toString())
//            }.onFailure { exception ->
//                withContext(Dispatchers.Main) {
//                    Log.e("viewmodel search Error", "Failed to fetch trending videos", exception)
//                }
//            }
//        }
//    }
    private fun convertToSearchItemModel(item: SearchItem): VideoDetailModel {
//           TODO : 추가할 수 있으면 viewCount도 추가하기
        return VideoDetailModel(
            id = item.id.videoId,
            thumbNailUrl = item.snippet.thumbnails.default.url,
            channelId = item.snippet.channelId,
            channelTitle = item.snippet.channelTitle,
            title = item.snippet.title,
            description = item.snippet.description,
            publishTime = item.snippet.publishedAt,
            channelInfoModel = null,
            videoViewCount = null
        )
    }
}