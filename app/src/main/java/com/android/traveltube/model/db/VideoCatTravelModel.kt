package com.android.traveltube.model.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.traveltube.model.ChannelInfoModel
import com.android.traveltube.model.VideoViewCountModel
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
@Entity(tableName = "travel_videos")
data class VideoCatTravelModel(
    @PrimaryKey(autoGenerate = false) // 아이템을 구분할 때 사용할 고유 키
    val id: String,
    val thumbNailUrl: String?,
    val channelId: String?,
    val channelTitle: String?,
    val title: String?,
    val description: String?,
    val publishTime: Date?,
    val channelInfoModel: ChannelInfoModel? = null,
    val videoViewCountModel: VideoViewCountModel? = null,
    val isFavorite: Boolean = false
) : Parcelable