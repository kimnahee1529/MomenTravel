package com.android.traveltube.model.db

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoViewCountModel (
    val videoId: String,
    val viewCount: String?,
    val commentCount: String?,
    val likeCount: String?,
) : Parcelable