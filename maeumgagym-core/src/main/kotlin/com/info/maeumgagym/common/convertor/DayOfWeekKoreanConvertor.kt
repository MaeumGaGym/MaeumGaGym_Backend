package com.info.maeumgagym.common.convertor

import java.time.DayOfWeek

object DayOfWeekKoreanConvertor {

    private val koreanKeys = mapOf(
        "일요일" to DayOfWeek.SUNDAY,
        "월요일" to DayOfWeek.MONDAY,
        "화요일" to DayOfWeek.TUESDAY,
        "수요일" to DayOfWeek.WEDNESDAY,
        "목요일" to DayOfWeek.THURSDAY,
        "금요일" to DayOfWeek.FRIDAY,
        "토요일" to DayOfWeek.SATURDAY
    )

    private val dayOfWeekKeys = mapOf(
        DayOfWeek.SUNDAY to "일요일",
        DayOfWeek.MONDAY to "월요일",
        DayOfWeek.TUESDAY to "화요일",
        DayOfWeek.WEDNESDAY to "수요일",
        DayOfWeek.THURSDAY to "목요일",
        DayOfWeek.FRIDAY to "금요일",
        DayOfWeek.SATURDAY to "토요일"
    )

    fun koreanToDayOfWeek(string: String): DayOfWeek =
        koreanKeys[string] ?: throw IllegalArgumentException("Unknown DayOfWeek data")

    fun dayOfWeekToKorean(dayOfWeek: DayOfWeek): String =
        dayOfWeekKeys[dayOfWeek] ?: throw IllegalArgumentException("Unknown DayOfWeek data")
}
