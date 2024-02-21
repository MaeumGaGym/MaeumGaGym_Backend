package com.info.maeumgagym.common.convertor

import java.time.DateTimeException
import java.time.LocalDate

object LocalDateConvertor {

    fun localDateToString(localDate: LocalDate): String =
        localDate.year.toString() + "." + localDate.monthValue + "." + localDate.dayOfMonth

    fun stringToLocalDate(string: String): LocalDate {
        val year: Int
        val month: Int
        val day: Int

        string.split('-').run {
            year = get(0).toInt()
            month = get(1).toInt()
            day = get(2).toInt()
        }

        verifyYearRange(year)
        verifyMonthRange(month)
        verifyDayOfMonthRange(year, month, day)

        return LocalDate.of(year, month, day)
    }

    private fun verifyYearRange(year: Int) {
        if (year / 1000 < 1 || year / 1000 > 2) {
            throwYearRangeException()
        }
    }

    private fun verifyMonthRange(month: Int) {
        if (month > 12 || month < 1) {
            throwMonthRangeException()
        }
    }

    private fun verifyDayOfMonthRange(year: Int, month: Int, day: Int) {
        val maxDay = when (month) {
            2 -> (if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) 29 else 28)
            4, 6, 9, 11 -> 30
            else -> 31
        }

        if (day > maxDay || day < 1) {
            throwDayRangeException(maxDay)
        }
    }

    private fun throwYearRangeException(): Nothing {
        throw DateTimeException("The Range of Year MUST BE BETWEEN 1000 and 2000")
    }

    private fun throwMonthRangeException(): Nothing {
        throw DateTimeException("The Range of Month MUST BE BETWEEN 1 and 12")
    }

    private fun throwDayRangeException(max: Int): Nothing {
        throw DateTimeException("The Range of Month MUST BE BETWEEN 1 and $max")
    }
}
