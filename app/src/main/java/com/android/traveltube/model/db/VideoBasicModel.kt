package com.android.traveltube.model.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.traveltube.data.db.ModelType
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "videos")
data class VideoBasicModel(
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
    val modelType: ModelType,
    val isFavorite: Boolean = false
) : Parcelable