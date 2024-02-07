package com.android.traveltube.data.search

import com.google.gson.annotations.SerializedName

data class Medium(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int
)