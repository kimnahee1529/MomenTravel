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
    //일 수 계산하기 위한 함수
    fun Date.dateFormatter(): String {
        val dateInstant = Instant.ofEpochMilli(this.time)
        val date = LocalDateTime.ofInstant(dateInstant, ZoneId.systemDefault())

        val nowInstant = Instant.ofEpochMilli(System.currentTimeMillis())
        val now = LocalDateTime.ofInstant(nowInstant, ZoneId.systemDefault())

        val hoursDifference = ChronoUnit.HOURS.between(date, now)
        val daysDifference = ChronoUnit.DAYS.between(date, now)
        val monthsDifference = ChronoUnit.MONTHS.between(date, now)
        val yearsDifference = ChronoUnit.YEARS.between(date, now)

        return when {
            daysDifference < 1 -> {
                if (hoursDifference < 1) {
                    "${ChronoUnit.MINUTES.between(date, now)}분 전"
                } else {
                    "${hoursDifference}시간 전"
                }
            }

            daysDifference < 30 -> "${daysDifference}일 전"
            monthsDifference < 1 -> "한 달 전"
            monthsDifference < 12 -> "${monthsDifference}개월 전"
            else -> "${yearsDifference}년 전"
        }
    }

    //구독자 표시하기 위한 함수
    fun String.convertToDecimalString(): String {
        val number = this.toLongOrNull() ?: return ""
        val result = String.format("%.1f", number.toDouble() / 1000.0)
        return if (result.endsWith(".0")) result.dropLast(2) else result
    }

    fun String.formatNumber(): String {
        val count = this.toIntOrNull() ?: 0

        return when {
            count >= 10000 -> {
                val tenThousandCount = count / 10000.0
                val formattedCount = String.format("%.1f", tenThousandCount)
                if (formattedCount.endsWith(".0")) {
                    "${formattedCount.substringBefore('.')}만회"
                } else {
                    "${formattedCount}만회"
                }
            }
            count >= 1000 -> {
                val thousandCount = count / 1000.0
                val formattedCount = String.format("%.1f", thousandCount)
                if (formattedCount.endsWith(".0")) {
                    "${formattedCount.substringBefore('.')}천회"
                } else {
                    "${formattedCount}천회"
                }
            }
            else -> {
                "${count}회"
            }
        }
    }
}
