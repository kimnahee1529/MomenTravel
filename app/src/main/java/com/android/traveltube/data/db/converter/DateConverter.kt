package com.android.traveltube.data.db.converter

import androidx.room.TypeConverter
import com.android.traveltube.model.db.ChannelInfoModel
import com.android.traveltube.model.db.VideoViewCountModel
import com.google.gson.Gson
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    @TypeConverter
    fun toDate(date: Date?): Long? {
        return date?.time
    }
}

class ChannelInfoModelConverter {
    @TypeConverter
    fun fromChannelInfoModel(channelInfoModel: ChannelInfoModel?): String? {
        return channelInfoModel?.let { Gson().toJson(it) }
    }
    @TypeConverter
    fun toChannelInfoModel(json: String?): ChannelInfoModel? {
        return json?.let { Gson().fromJson(it, ChannelInfoModel::class.java) }
    }
}

class VideoViewCountConverter {
    @TypeConverter
    fun fromVideoViewCountModel(videoViewCountModel: VideoViewCountModel?): String? {
        return videoViewCountModel?.let { Gson().toJson(it) }
    }
    @TypeConverter
    fun toVideoViewCountModel(json: String?): VideoViewCountModel? {
        return json?.let { Gson().fromJson(it, VideoViewCountModel::class.java) }
    }
}