package com.android.traveltube.network

import com.android.traveltube.data.search.SearchModel
import com.android.traveltube.data.videos.VideoModel
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_MAX_RESULT = 3
private const val API_REGION = "KR"
private const val youtubeApiKey = "" //TODO API키 넣어주세요

interface YouTubeAPI {
    /*
    Videos: list
    동영상 정보 가져오는 api
    */
    @GET("videos")
    suspend fun getTrendingVideos(
        @Query("part")
        part: String = "snippet",
        @Query("chart")
        chart: String = "mostPopular",
        @Query("maxResults")
        maxResults: Int = API_MAX_RESULT,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("key")
        apiKey: String = youtubeApiKey
    ): VideoModel

    /*
    Search: list
    검색 정보 가져오는 api
    */
    @GET("search")
    suspend fun getSearchingVideos(
        @Query("part")
        part: String = "snippet",
        @Query("q")
        searchText: String = "플레이브 봉구",
        @Query("maxResults")
        maxResults: Int = API_MAX_RESULT,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("key")
        apiKey: String = youtubeApiKey
    ): SearchModel

}