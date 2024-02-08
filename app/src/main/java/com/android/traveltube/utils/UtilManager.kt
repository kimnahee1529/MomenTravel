package com.android.traveltube.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date

object UtilManager {
    fun ImageView.loadImage(url: String){
        Glide.with(this)
            .load(url)
            .into(this)
    }
}

object DateManager {

    fun Date.dateFormatter(): String {
        // Date를 LocalDateTime으로 변환
        val dateInstant = Instant.ofEpochMilli(this.time)
        val date = LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault())

        // 현재 날짜 및 시간
        val nowInstant = Instant.ofEpochMilli(System.currentTimeMillis())
        val now = LocalDateTime.ofInstant(nowInstant, ZoneId.systemDefault())

        // 날짜 차이 계산
        val daysDifference = ChronoUnit.DAYS.between(date, now)
        val monthsDifference = ChronoUnit.MONTHS.between(date, now)
        val yearsDifference = ChronoUnit.YEARS.between(date, now)

        // 결과 출력
        return when {
            daysDifference < 1 -> "하루 전"
            daysDifference < 30 -> "${daysDifference}일 전"
            monthsDifference < 1 -> "한 달 전"
            monthsDifference < 12 -> "${monthsDifference}달 전"
            else -> "${yearsDifference}년 전"
        }
    }

}