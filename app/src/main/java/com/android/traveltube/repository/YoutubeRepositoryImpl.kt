package com.android.traveltube.repository

import androidx.lifecycle.LiveData
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.data.db.VideoSearchDatabase
import com.android.traveltube.model.db.VideoFavoriteModel
import com.android.traveltube.model.db.VideoBasicModel
import com.android.traveltube.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YoutubeRepositoryImpl(private val db: VideoSearchDatabase) {
    suspend fun getTrendingVideos() = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getTrendingVideos()
    }

    suspend fun getSearchingVideos(search: String = "일본 오사카") = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getSearchingVideos(searchText = search)
    }

    suspend fun getCatTravelVideos(search: String) = withContext(Dispatchers.IO) {
        RetrofitInstance.api.getCatTravelVideos(searchText = search)
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

    fun getFavoriteVideos(): LiveData<List<VideoFavoriteModel>> =
        db.videoFavoriteDao().getFavoriteVideos()

    suspend fun insertVideos(model: List<VideoBasicModel>) {
        db.videoDao().insertVideos(model)
    }

    fun getVideos(): LiveData<List<VideoBasicModel>> =
        db.videoDao().getVideos()

    fun getVideos(modelType: ModelType): LiveData<List<VideoBasicModel>> =
        db.videoDao().getVideosByModelType(modelType)

    suspend fun updateFavoriteStatus(videoId: String, isFavorite: Boolean) {
        val video = db.videoDao().getVideoById(videoId)
        video?.let {
            db.videoDao().updateFavoriteStatus(videoId, isFavorite)

            val favoriteVideo = VideoFavoriteModel(
                id = it.id,
                thumbNailUrl = it.thumbNailUrl,
                channelId = it.channelId,
                channelTitle = it.channelTitle,
                title = it.title,
                description = it.description,
                publishTime = it.publishTime,
                channelInfoModel = it.channelInfoModel,
                videoViewCountModel = it.videoViewCountModel,
            )

            if (isFavorite) {
                db.videoFavoriteDao().insertFavoriteVideo(favoriteVideo)
            } else {
                db.videoFavoriteDao().deleteFavoriteVideo(favoriteVideo)
            }
        }
    }
}
