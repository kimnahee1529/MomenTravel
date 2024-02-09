package com.android.traveltube.ui.datail

import com.android.traveltube.model.VideoDetailModel

sealed interface DetailContentEvent {
    data class OpenContent(
        val position: Int,
        val item: VideoDetailModel
    ) : DetailContentEvent
}