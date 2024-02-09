package com.android.traveltube.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface VideoDao<T>  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(models: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(models: T)

    @Delete
    suspend fun deleteVideo(models: T)

    fun getVideos(): LiveData<List<T>>
}