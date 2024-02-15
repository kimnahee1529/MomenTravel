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
) : ViewModel() {
    private val _videoData = MutableLiveData<List<VideoDetailModel>>()
    val videoData: LiveData<List<VideoDetailModel>> = _videoData

    private fun convertToSearchItemModel(item: SearchItem): VideoDetailModel {
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