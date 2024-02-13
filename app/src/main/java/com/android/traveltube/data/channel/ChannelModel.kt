package com.android.traveltube.data.channel

import com.google.gson.annotations.SerializedName

data class ChannelModel(
    @SerializedName("etag") val eTag: String,
    val items: List<Item>,
    val kind: String,
    val pageInfo: PageInfo
)