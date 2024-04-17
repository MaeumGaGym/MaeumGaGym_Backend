package com.info.maeumgagym.common.convertor

import com.info.maeumgagym.common.exception.PresentationValidationException
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

object DayOfWeekConvertor {

    private val koreanCharKeys = mapOf(
        "일" to DayOfWeek.SUNDAY,
        "월" to DayOfWeek.MONDAY,
        "화" to DayOfWeek.TUESDAY,
        "수" to DayOfWeek.WEDNESDAY,
        "목" to DayOfWeek.THURSDAY,
        "금" to DayOfWeek.FRIDAY,
        "토" to DayOfWeek.SATURDAY
    )

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
            if (string.length == 1) {
                koreanCharKeys[string]
            } else {
                koreanKeys[string]
            } ?: DayOfWeek.valueOf(string.uppercase())
        } catch (e: Exception) {
            throwDayOfWeekWrongException()
        }

    fun dayOfWeekToKorean(dayOfWeek: DayOfWeek): String =
        dayOfWeek.getDisplayName(
            TextStyle.FULL,
            Locale.KOREAN
        )

    private fun throwDayOfWeekWrongException(): Nothing =
        throw PresentationValidationException(
            400,
            "DayOfWeek input wrong",
            mapOf(
                Pair("day_of_weeks", "DayOfWeek input wrong")
            )
        )
}
