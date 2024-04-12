package com.info.maeumgagym.common.convertor

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

object DayOfWeekConvertor {

    private val koreanKeys = mapOf(
        "일요일" to DayOfWeek.SUNDAY,
        "월요일" to DayOfWeek.MONDAY,
        "화요일" to DayOfWeek.TUESDAY,
        "수요일" to DayOfWeek.WEDNESDAY,
        "목요일" to DayOfWeek.THURSDAY,
        "금요일" to DayOfWeek.FRIDAY,
        "토요일" to DayOfWeek.SATURDAY
    )

    fun stringToDayOfWeek(string: String): DayOfWeek =
        try {
            koreanKeys[string] ?: DayOfWeek.valueOf(string.uppercase())
        } catch (e: Exception) {
            throw IllegalArgumentException("DayOfWeek input wrong")
        }

    fun dayOfWeekToKorean(dayOfWeek: DayOfWeek): String =
        dayOfWeek.getDisplayName(
            TextStyle.FULL,
            Locale.KOREAN
        )
}

