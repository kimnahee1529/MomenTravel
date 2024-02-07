package com.android.traveltube.data.videos

import com.google.gson.annotations.SerializedName

data class VideoModel(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("etag")
    val eTag: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo
)