package com.android.traveltube.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.traveltube.model.db.VideoFavoriteModel

@Dao
interface VideoFavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteVideo(model: VideoFavoriteModel)
    @Delete
    suspend fun deleteFavoriteVideo(model: VideoFavoriteModel)
    @Query("SELECT * FROM favorite_videos")
    fun getFavoriteVideos(): LiveData<List<VideoFavoriteModel>>
}