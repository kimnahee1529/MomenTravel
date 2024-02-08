package com.android.traveltube.network

import com.android.traveltube.data.search.SearchModel
import com.android.traveltube.data.videos.VideoModel
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_MAX_RESULT = 5
private const val API_REGION = "KR"
private const val API_KEY = "AIzaSyDC8ZIONu13jIWzHP6ldjrrZ-uj-Az_VQk" //TODO API키 넣어주세요
private const val CHANNEL_ID = "UC6KwCU8Y8Uw4h_Q0ptLZkqw"
private const val SEARCH_TEXT = "플레이브 봉구"

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
        apiKey: String = API_KEY
    ): VideoModel

    /*
    Search: list
    영상 검색 정보 가져오는 api
    */
    @GET("search")
    suspend fun getSearchingVideos(
        @Query("part")
        part: String = "snippet",
        @Query("q")
        searchText: String = SEARCH_TEXT,
        @Query("maxResults")
        maxResults: Int = API_MAX_RESULT,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("key")
        apiKey: String = API_KEY
    ): SearchModel
    /*
    Search: list
    채널 검색 정보 가져오는 api
    */
    @GET("search")
    suspend fun getChannelsVideo(
        @Query("part")
        part: String = "snippet",
        @Query("channelId")
        channelId: String = CHANNEL_ID,
        @Query("maxResults")
        maxResults: Int = API_MAX_RESULT,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("key")
        apiKey: String = API_KEY
    ): SearchModel
}