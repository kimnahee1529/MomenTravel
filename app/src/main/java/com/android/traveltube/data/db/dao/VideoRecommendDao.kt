package com.android.traveltube.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.android.traveltube.model.db.VideoRecommendModel

@Dao
interface VideoRecommendDao : VideoDao<VideoRecommendModel> {
    @Query("SELECT * FROM recommend_videos")
    suspend fun getVideos(): List<VideoRecommendModel>
}