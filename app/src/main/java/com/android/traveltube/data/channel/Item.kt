package com.android.traveltube.data.channel

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("etag")
    val eTag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet,
    val statistics: Statistics
)