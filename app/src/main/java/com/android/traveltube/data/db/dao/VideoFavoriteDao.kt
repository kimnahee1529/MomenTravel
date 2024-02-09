package com.android.traveltube.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.android.traveltube.model.db.VideoFavoriteModel

@Dao
interface VideoFavoriteDao : VideoDao<VideoFavoriteModel> {
    @Query("SELECT * FROM favorite_videos")
    suspend fun getVideos(): List<VideoFavoriteModel>
}