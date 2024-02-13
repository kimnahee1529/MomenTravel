package com.android.traveltube.model.db

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChannelInfoModel(
    val channelId: String?,
    val channelThumbnail: String?,
    val subscriberCount: String?,
    val hiddenSubscriberCount: Boolean?
) : Parcelable