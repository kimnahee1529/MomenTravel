package com.android.traveltube.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

object UtilManager {
    fun ImageView.loadImage(url: String) {
        this.clipToOutline = true
        Glide.with(this)
            .load(url)
            .into(this)
    }
}

object DateManager {
    fun Date.dateFormatter(): String {
        val dateInstant = Instant.ofEpochMilli(this.time)
        val date = LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault())

        val nowInstant = Instant.ofEpochMilli(System.currentTimeMillis())
        val now = LocalDateTime.ofInstant(nowInstant, ZoneId.systemDefault())

        val daysDifference = ChronoUnit.DAYS.between(date, now)
        val monthsDifference = ChronoUnit.MONTHS.between(date, now)
        val yearsDifference = ChronoUnit.YEARS.between(date, now)

        return when {
            daysDifference < 1 -> "하루 전"
            daysDifference < 30 -> "${daysDifference}일 전"
            monthsDifference < 1 -> "한 달 전"
            monthsDifference < 12 -> "${monthsDifference}달 전"
            else -> "${yearsDifference}년 전"
        }
    }
    fun String.convertToDecimalString(): String {
        val number = this.toLongOrNull() ?: return ""
        val result = String.format("%.1f", number.toDouble() / 1000.0)
        return if (result.endsWith(".0")) result.dropLast(2) else result
    }
}