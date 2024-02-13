package com.android.traveltube.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorite_videos")
data class VideoFavoriteModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val thumbNailUrl: String?,
    val channelId: String?,
    val channelTitle: String?,
    val title: String?,
    val description: String?,
    val publishTime: Date?,
    val channelInfoModel: ChannelInfoModel?,
    val videoViewCountModel: VideoViewCountModel?,
)