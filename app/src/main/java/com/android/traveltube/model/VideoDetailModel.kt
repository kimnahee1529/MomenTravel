package com.android.traveltube.model

import android.os.Parcelable
import com.android.traveltube.model.db.ChannelInfoModel
import com.android.traveltube.model.db.VideoViewCountModel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class VideoDetailModel(
    val id: String,
    val thumbNailUrl: String?,
    val channelId: String?,
    val channelTitle: String?,
    val title: String?,
    val description: String?,
    val publishTime: Date?,
    val channelInfoModel: ChannelInfoModel?,
    val videoViewCount: VideoViewCountModel?,
    val isFavorite: Boolean = false
) : Parcelable