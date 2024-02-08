package com.android.traveltube.ui.datail

import java.util.Date

/**
 * 영상 : videoId
 * 영상 제목 : title
 * 영상 설명 : description
 * 날짜 : date
 * 채널 이름 : ?
 * 채널 썸네일 : ?
 * 구독자 수 : ?
 */
data class DetailUiState(
    val videoId: String?,
    val videoTitle: String?,
    val videoDescription: String?,
    val videoDate: Date?,
    val channelName: String?,
    val channelThumbnail: String?,
    val subscriptionCount: Int?
) {
    companion object {
        fun init() = DetailUiState(
            videoId = null,
            videoTitle = null,
            videoDescription = null,
            videoDate = null,
            channelName = null,
            channelThumbnail = null,
            subscriptionCount = null
        )
    }
}