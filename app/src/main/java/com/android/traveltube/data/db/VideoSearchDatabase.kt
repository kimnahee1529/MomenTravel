package com.android.traveltube.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.traveltube.data.db.dao.VideoFavoriteDao
import com.android.traveltube.data.db.dao.VideoRecommendDao
import com.android.traveltube.model.VideoDetailModel
import com.android.traveltube.data.db.converter.ChannelInfoModelConverter
import com.android.traveltube.data.db.converter.DateConverter
import com.android.traveltube.model.db.VideoFavoriteModel
import com.android.traveltube.model.db.VideoRecommendModel

// Room에서 사용할 Entity, DB version, exportSchema 여부를 지정
@Database(
    entities = [VideoFavoriteModel::class, VideoRecommendModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, ChannelInfoModelConverter::class)
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
            ).build()

        fun getInstance(context: Context): VideoSearchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}

/**
 * 추천 여행지 리스트 RecommendVideoList v
 * 여행 카테고리 리스트 TravelVideoList
 * 좋아요 리스트 FavoriteList v
 * 시청기록 리스트 ViewingRecordList
 */