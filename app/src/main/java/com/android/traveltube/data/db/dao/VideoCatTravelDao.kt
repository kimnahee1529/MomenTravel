package com.android.traveltube.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.android.traveltube.model.db.VideoCatTravelModel

@Dao
interface VideoCatTravelDao : VideoDao<VideoCatTravelModel> {
    @Query("SELECT * FROM travel_videos")
    suspend fun getVideos(): List<VideoCatTravelModel>
}