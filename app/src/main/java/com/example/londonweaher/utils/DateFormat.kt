package com.example.londonweaher.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class DateFormat {
    companion object {
         fun formDate(timestamp: String): String {
            val dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp.toLong()),
                ZoneId.systemDefault()
            )
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            return dateTime.format(formatter)
        }
    }
}

