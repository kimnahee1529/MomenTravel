package com.android.traveltube.repository

import androidx.lifecycle.LiveData
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.data.db.VideoSearchDatabase
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

    suspend fun getFavoriteVideos(): List<VideoBasicModel> =
        db.videoDao().getFavoriteVideos()

    suspend fun insertVideos(model: List<VideoBasicModel>) {
        for (newVideo in model) {
            val existingVideo = db.videoDao().getVideoById(newVideo.id)
            if (existingVideo != null) {
                val updatedVideo = existingVideo.copy(
                    thumbNailUrl = newVideo.thumbNailUrl ?: existingVideo.thumbNailUrl,
                    channelId = newVideo.channelId ?: existingVideo.channelId,
                    channelTitle = newVideo.channelTitle ?: existingVideo.channelTitle,
                    title = newVideo.title ?: existingVideo.title,
                    description = newVideo.description ?: existingVideo.description,
                    publishTime = newVideo.publishTime ?: existingVideo.publishTime,
                    channelInfoModel = newVideo.channelInfoModel ?: existingVideo.channelInfoModel,
                    videoViewCountModel = newVideo.videoViewCountModel ?: existingVideo.videoViewCountModel
                )
                db.videoDao().insertVideos(listOf(updatedVideo))
            } else {
                db.videoDao().insertVideos(listOf(newVideo))
            }
        }
    }

    fun getVideos(modelType: ModelType): LiveData<List<VideoBasicModel>> =
        db.videoDao().getVideosByModelType(modelType)

    suspend fun updateFavoriteStatus(videoId: String, isFavorite: Boolean) {
        db.videoDao().updateFavoriteStatus(videoId, isFavorite)
    }

    suspend fun updateSavedStatus(videoId: String, isSaved: Boolean) {
        db.videoDao().updateIsSavedStatus(videoId, isSaved)
    }

    fun getSavedVideos(): LiveData<List<VideoBasicModel>> =
        db.videoDao().getSavedVideos()

}
