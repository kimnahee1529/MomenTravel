package com.android.traveltube.ui.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.viewmodel.BasicDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailCityViewModel(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl
) : BasicDetailViewModel() {
    private var searchVideoListSuccess = false
    private var searchTravelVideoListSuccess = false
    private var searchTravelShortsVideoListSuccess = false
    private val _bothSearchesSuccessful = MutableLiveData<Boolean>()
    val bothSearchesSuccessful: LiveData<Boolean>
        get() = _bothSearchesSuccessful

    private fun saveSearchResult(results: List<VideoBasicModel>) =
        viewModelScope.launch {
            youtubeRepositoryImpl.insertVideos(results)
            checkBothSearches()
        }

    private fun checkBothSearches() {
        if (searchVideoListSuccess && searchTravelVideoListSuccess && searchTravelShortsVideoListSuccess) {
            _bothSearchesSuccessful.value = true
        }
    }

    fun getSearchVideoList() {
        searchVideoList()
    }

    fun getTravelVideoList() {
        searchTravelVideoList()
    }

    fun getShortsVideoList() {
        searchShortsVideoList()
    }

    //영상 검색 정보 가져오는 getSearchingVideos 호출
    private fun searchVideoList() = viewModelScope.launch {
        kotlin.runCatching {
            val videos = youtubeRepositoryImpl.getSearchingVideos()
            val videoItemModels = videos.items.map { item ->
                val channelInfoList = getChannelInfo(item.snippet.channelId, youtubeRepositoryImpl)
                val videoViewCountList = getVideoViewCount(item.id.videoId, youtubeRepositoryImpl)
                val videoViewCountModel = videoViewCountList.firstOrNull()

                VideoBasicModel(
                    id = item.id.videoId,
                    thumbNailUrl = item.snippet.thumbnails.medium.url,
                    channelId = item.snippet.channelId,
                    channelTitle = item.snippet.channelTitle,
                    title = item.snippet.title,
                    description = item.snippet.description,
                    publishTime = item.snippet.publishedAt,
                    channelInfoModel = channelInfoList.first(),
                    videoViewCountModel = videoViewCountModel,
                    modelType = ModelType.VIDEO_RECOMMEND
                )
            }

            saveSearchResult(videoItemModels)
            searchVideoListSuccess = true
        }.onFailure { exception ->
            withContext(Dispatchers.Main) {
                Log.e(
                    "DetailCityViewModel",
                    "Failed to fetch search videos",
                    exception
                )
                searchVideoListSuccess = false
            }
        }
    }

    //여행 카테고리 영상 검색 정보 가져오는 getCatTravelVideos 호출
    private fun searchTravelVideoList() = viewModelScope.launch {
        kotlin.runCatching {
            val videos = youtubeRepositoryImpl.getCatTravelVideos("여행 -쇼츠 -shorts")
            val videoItemModels = videos.items.map { item ->
                val channelInfoList = getChannelInfo(item.snippet.channelId, youtubeRepositoryImpl)
                val videoViewCountList = getVideoViewCount(item.id.videoId, youtubeRepositoryImpl)
                val videoViewCountModel = videoViewCountList.firstOrNull()

                VideoBasicModel(
                    id = item.id.videoId,
                    thumbNailUrl = item.snippet.thumbnails.medium.url,
                    channelId = item.snippet.channelId,
                    channelTitle = item.snippet.channelTitle,
                    title = item.snippet.title,
                    description = item.snippet.description,
                    publishTime = item.snippet.publishedAt,
                    channelInfoModel = channelInfoList.first(),
                    videoViewCountModel = videoViewCountModel,
                    modelType = ModelType.VIDEO_CATEGORY_TRAVEL
                )
            }

            saveSearchResult(videoItemModels)
            searchTravelVideoListSuccess = true
        }.onFailure { exception ->
            withContext(Dispatchers.Main) {
                Log.e(
                    "sharedviewmodel detailItem search Error",
                    "Failed to fetch trending videos",
                    exception
                )
                searchVideoListSuccess = false
            }
        }
    }

    //여행 쇼츠 영상 검색 정보 가져오는 getCatTravelVideos 호출
    private fun searchShortsVideoList() = viewModelScope.launch {
        kotlin.runCatching {
            val videos = youtubeRepositoryImpl.getCatTravelVideos("여행 (쇼츠|shorts)")
            val videoItemModels = videos.items.map { item ->
                val channelInfoList = getChannelInfo(item.snippet.channelId, youtubeRepositoryImpl)
                val videoViewCountList = getVideoViewCount(item.id.videoId, youtubeRepositoryImpl)
                val videoViewCountModel = videoViewCountList.firstOrNull()

                VideoBasicModel(
                    id = item.id.videoId ?: item.id.kind,
                    thumbNailUrl = item.snippet.thumbnails.medium.url,
                    channelId = item.snippet.channelId,
                    channelTitle = item.snippet.channelTitle,
                    title = item.snippet.title,
                    description = item.snippet.description,
                    publishTime = item.snippet.publishedAt,
                    channelInfoModel = channelInfoList.first(),
                    videoViewCountModel = videoViewCountModel,
                    modelType = ModelType.VIDEO_CATEGORY_SHORTS
                )
            }
            saveSearchResult(videoItemModels)
            searchTravelShortsVideoListSuccess = true
        }.onFailure { exception ->
            withContext(Dispatchers.Main) {
                Log.e(
                    "sharedviewmodel detailItem search Error",
                    "Failed to fetch trending videos",
                    exception
                )
                searchTravelShortsVideoListSuccess = false
            }
        }
    }

}

class DetailCityViewModelProviderFactory(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailCityViewModel::class.java)) {
            return DetailCityViewModel(youtubeRepositoryImpl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}