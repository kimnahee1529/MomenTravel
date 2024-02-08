package com.android.traveltube.data.search

import java.util.Date

data class Snippet(
    val publishedAt: Date,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String,
    val publishTime: Date
)