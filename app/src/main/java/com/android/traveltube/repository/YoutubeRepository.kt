package com.android.traveltube.repository

import com.android.traveltube.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YoutubeRepository {
    // 어떤 함수인지 적어놔야 할까?
    suspend fun getTrendingVideos() = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getTrendingVideos()
    }

    suspend fun getSearchingVideos() = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getSearchingVideos()
    }

    suspend fun getChannelsVideo() = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getChannelsVideo()
    }


    suspend fun getChannelInfo(channelId: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getChannelInfo(channelId = channelId)
    }
}