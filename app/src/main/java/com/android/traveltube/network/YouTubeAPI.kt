package com.android.traveltube.network

import com.android.traveltube.data.channel.ChannelModel
import com.android.traveltube.data.search.SearchModel
import com.android.traveltube.data.videos.VideoModel
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_MAX_RESULT = 15 //TODO 수정 필요
private const val API_REGION = "KR"
private const val API_KEY = "AIzaSyDTAGRYG7esqNg9PlYNPALqVEZrwfpCgj0" //TODO API키 넣어주세요
private const val CHANNEL_ID = "UC6KwCU8Y8Uw4h_Q0ptLZkqw" //TODO 수정 필요
private const val SEARCH_TEXT = "플레이브 봉구" //TODO 수정 필요
private const val VIDEO_ID = "gbxCTwgKcX8" //TODO 수정 필요


interface YouTubeAPI {
    /**
    Videos: list
    인기 비디오 목록을 조회하는 api
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

    /**
    Videos: list
    동영상 조회수 가져오는 api
     */
    @GET("videos")
    suspend fun getViewCount(
        @Query("part")
        part: String = "snippet,statistics",
        @Query("id")
        id: String,
        @Query("maxResults")
        maxResults: Int = API_MAX_RESULT,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("key")
        apiKey: String = API_KEY
    ): VideoModel

    /**
    Search: list
    영상 검색 정보 가져오는 api
     */
    @GET("search")
    suspend fun getSearchingVideos(
        @Query("part")
        part: String = "snippet",
        @Query("q")
        searchText: String,
        @Query("maxResults")
        maxResults: Int = API_MAX_RESULT,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("key")
        apiKey: String = API_KEY
    ): SearchModel

    /**
    Search: list
    카테고리가 여행인 검색 정보 가져오는 api
     */
    @GET("search")
    suspend fun getCatTravelVideos(
        @Query("part")
        part: String = "snippet",
        @Query("order")
        order: String = "viewCount",
        @Query("q")
        searchText: String,
        @Query("maxResults")
        maxResults: Int = API_MAX_RESULT,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("type")
        type: String = "video",
        @Query("videoCategoryId")
        videoCategoryId: String = "19",
        @Query("key")
        apiKey: String = API_KEY
    ): SearchModel

    /**
    Channels: list
    채널 정보 가져오는 api
     */
    @GET("channels")
    suspend fun getChannelInfo(
        @Query("part")
        part: String = "snippet, statistics",
        @Query("id")
        channelId: String,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("key")
        apiKey: String = API_KEY
    ): ChannelModel

    /**
    Search: list
    채널 검색 정보 가져오는 api
     */
    @GET("search")
    suspend fun getChannelsVideo(
        @Query("part")
        part: String = "snippet",
        @Query("channelId")
        channelId: String,
        @Query("maxResults")
        maxResults: Int = API_MAX_RESULT,
        @Query("regionCode")
        regionCode: String = API_REGION,
        @Query("key")
        apiKey: String = API_KEY
    ): SearchModel

}