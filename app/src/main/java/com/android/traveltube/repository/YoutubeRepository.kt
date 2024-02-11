package com.android.traveltube.repository

import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.model.db.VideoCatTravelModel
import com.android.traveltube.model.db.VideoFavoriteModel
import com.android.traveltube.model.db.VideoRecommendModel
import com.android.traveltube.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YoutubeRepository(
    private val db: VideoSearchDatabase
) {
    // 어떤 함수인지 적어놔야 할까?
    suspend fun getTrendingVideos() = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getTrendingVideos()
    }

    suspend fun getSearchingVideos() = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getSearchingVideos()
    }

    suspend fun getCatTravelVideos() = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getCatTravelVideos()
    }

    suspend fun getChannelInfo(channelId: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getChannelInfo(channelId = channelId)
    }

    suspend fun getViewCount(videoId: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getViewCount(id = videoId)
    }

    suspend fun getChannelVideos(channelId: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getChannelsVideo(channelId = channelId)
    }

    suspend fun insertFavoriteVideo(model: VideoFavoriteModel) {
        db.videoFavoriteDao().insertVideo(model)
    }

    suspend fun deleteFavoriteVideo(model: VideoFavoriteModel) {
        db.videoFavoriteDao().deleteVideo(model)
    }

    suspend fun getFavoriteVideos(): List<VideoFavoriteModel> {
        return db.videoFavoriteDao().getVideos()
    }

    // 추천 여행지 리스트 추가
    suspend fun insertRecommendVideo(model: List<VideoRecommendModel>) {
        db.videoRecommendDao().insertVideos(model)
    }

    // 추천 여행지 리스트 가져 오기
    suspend fun getRecommendVideos(): List<VideoRecommendModel> {
        return db.videoRecommendDao().getVideos()
    }

    suspend fun insertCatTravelVideo(model: List<VideoCatTravelModel>){
        db.videoCatTravelDao().insertVideos(model)
    }

}