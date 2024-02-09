package com.android.traveltube.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.traveltube.data.db.converter.ChannelInfoModelConverter
import com.android.traveltube.data.db.converter.DateConverter
import com.android.traveltube.data.db.dao.VideoFavoriteDao
import com.android.traveltube.data.db.dao.VideoRecommendDao
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
    abstract fun videoFavoriteDao(): VideoFavoriteDao // Room에서 사용할 Dao
    abstract fun videoRecommendDao(): VideoRecommendDao // Room에서 사용할 Dao


    companion object {
        /**
         * 데이터베이스 객체도 생성하는데 비용이 많이 들기 때문에 중복으로 생성하지 않도록 싱글톤 생성
         */
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