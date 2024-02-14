package com.android.traveltube.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.traveltube.data.db.converter.ChannelInfoModelConverter
import com.android.traveltube.data.db.converter.DateConverter
import com.android.traveltube.data.db.converter.VideoViewCountConverter
import com.android.traveltube.data.db.dao.VideoDAO
import com.android.traveltube.model.db.VideoBasicModel


@Database(
    entities = [VideoBasicModel::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    ChannelInfoModelConverter::class,
    VideoViewCountConverter::class
)
abstract class VideoSearchDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDAO

    companion object {
        @Volatile
        private var INSTANCE: VideoSearchDatabase? = null

        private fun buildDatabase(context: Context): VideoSearchDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                VideoSearchDatabase::class.java,
                "videos"
            )
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): VideoSearchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}