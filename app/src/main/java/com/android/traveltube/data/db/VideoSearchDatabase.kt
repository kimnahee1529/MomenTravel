package com.android.traveltube.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.traveltube.data.db.converter.ChannelInfoModelConverter
import com.android.traveltube.data.db.converter.DateConverter
import com.android.traveltube.data.db.converter.VideoViewCountConverter
import com.android.traveltube.data.db.dao.VideoFavoriteDao
import com.android.traveltube.data.db.dao.VideoRecommendDao
import com.android.traveltube.model.db.VideoFavoriteModel
import com.android.traveltube.model.db.VideoRecommendModel

@Database(
    entities = [VideoFavoriteModel::class, VideoRecommendModel::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    ChannelInfoModelConverter::class,
    VideoViewCountConverter::class
)
abstract class VideoSearchDatabase : RoomDatabase() {
    abstract fun videoFavoriteDao(): VideoFavoriteDao
    abstract fun videoRecommendDao(): VideoRecommendDao

    companion object {
        @Volatile
        private var INSTANCE: VideoSearchDatabase? = null

        private fun buildDatabase(context: Context): VideoSearchDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                VideoSearchDatabase::class.java,
                "favorite-videos"
            )
                .fallbackToDestructiveMigration() // Handle schema changes (this discards the existing data)
                .build()

        fun getInstance(context: Context): VideoSearchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}