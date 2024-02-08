package com.android.traveltube.data.videos

import com.google.gson.annotations.SerializedName

data class Statistics(
    @SerializedName("viewCount")
    val viewCount: String,
    @SerializedName("likeCount")
    val likeCount: String,
    @SerializedName("favoriteCount")
    val favoriteCount: String,
    @SerializedName("commentCount")
    val commentCount: String
)
