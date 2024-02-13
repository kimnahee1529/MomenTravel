package com.android.traveltube.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoViewCountModel (
    val videoId: String,
    val viewCount: String?, // 조회수
    val commentCount: String?, // 댓글 수
    val likeCount: String?, // 동영상을 좋아한다고 표시한 사용자 수
) : Parcelable