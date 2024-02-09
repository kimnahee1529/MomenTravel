package com.android.traveltube.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.android.traveltube.model.db.VideoRecommendModel

@Dao
interface VideoRecommendDao : VideoDao<VideoRecommendModel> {
    @Query("SELECT * FROM recommend_videos")
    override fun getVideos(): LiveData<List<VideoRecommendModel>>
}