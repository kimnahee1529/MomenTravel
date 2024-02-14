package com.android.traveltube.viewmodel

import androidx.lifecycle.ViewModel
import com.android.traveltube.model.db.ChannelInfoModel
import com.android.traveltube.model.db.VideoViewCountModel
import com.android.traveltube.repository.YoutubeRepositoryImpl

abstract class BasicDetailViewModel: ViewModel() {

    suspend fun getChannelInfo(
        channelId: String,
        repository: YoutubeRepositoryImpl
    ): List<ChannelInfoModel> {
        return try {
            val channel = repository.getChannelInfo(channelId = channelId)
            channel.items.map { item ->
                convertToChannelInfoModel(item)
            }
        } catch (exception: Exception) {
            emptyList()
        }
    }

    suspend fun getVideoViewCount(
        videoId: String,
        repository: YoutubeRepositoryImpl
    ): List<VideoViewCountModel> {
        return try {
            val count = repository.getViewCount(videoId = videoId)
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

    private fun convertToViewCountModel(item: com.android.traveltube.data.videos.Item): VideoViewCountModel {
        return VideoViewCountModel(
            videoId = item.id,
            viewCount = item.statistics.viewCount,
            commentCount = item.statistics.commentCount,
            likeCount = item.statistics.likeCount
        )
    }
}