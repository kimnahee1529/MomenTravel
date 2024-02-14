package com.android.traveltube.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.traveltube.data.db.ModelType
import com.android.traveltube.model.db.VideoBasicModel

@Dao
interface VideoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(models: List<VideoBasicModel>)
    @Query("SELECT * FROM videos")
    fun getVideos(): LiveData<List<VideoBasicModel>>
    @Query("SELECT * FROM videos WHERE id = :itemId")
    suspend fun getVideoById(itemId: String): VideoBasicModel?
    @Query("UPDATE videos SET isFavorite = :isFavorite WHERE id = :videoId")
    suspend fun updateFavoriteStatus(videoId: String, isFavorite: Boolean)
    @Query("SELECT * FROM videos WHERE modelType = :modelType")
    fun getVideosByModelType(modelType: ModelType): LiveData<List<VideoBasicModel>>
    @Query("SELECT * FROM videos WHERE isFavorite = 1")
    suspend fun getFavoriteVideos(): List<VideoBasicModel>
    @Query("UPDATE videos SET isSaved = :isSaved WHERE id = :videoId")
    suspend fun updateIsSavedStatus(videoId: String, isSaved: Boolean)
    @Query("SELECT * FROM videos WHERE isSaved = 1")
    fun getSavedVideos(): LiveData<List<VideoBasicModel>>
}