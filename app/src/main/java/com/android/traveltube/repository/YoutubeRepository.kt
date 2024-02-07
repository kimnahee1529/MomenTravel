package com.android.traveltube.repository

import com.android.traveltube.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YoutubeRepository {
    // 어떤 함수인지 적어놔야 할까?
    suspend fun getTrendingVideos(region: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getTrendingVideos(regionCode = region).items
    }

    suspend fun getSearchingVideos(region: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getSearchingVideos(regionCode = region).items
    }
}