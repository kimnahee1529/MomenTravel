package com.android.traveltube.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.repository.YoutubeRepositoryImpl
import com.android.traveltube.viewmodel.BasicDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val youtubeRepositoryImpl: YoutubeRepositoryImpl
) : BasicDetailViewModel() {

    fun searchWithKeyWord(searchKey: String){
        searchVideo(searchKey)
    }
    private fun searchVideo(searchKey : String) = viewModelScope.launch {
        kotlin.runCatching {
            val videos = youtubeRepositoryImpl.getSearchingVideos(searchKey)
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
        }
    }
}
