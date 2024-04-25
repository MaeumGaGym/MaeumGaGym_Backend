package com.info.maeumgagym.common.convertor

import com.info.maeumgagym.common.exception.BusinessLogicException
import java.time.LocalDate

/**
 * [LocalDate]와 [String]간의 변환을 위한 object
 *
 * @throws BusinessLogicException [String]을 [LocalDate]로 변환하는 과정에서 발생하는 오류는 모두 400 Bad Request의 상태 코드를 갖습니다.
 */
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
        throw BusinessLogicException(400, "The Range of Year MUST BE BETWEEN 1000 and 2000")
    }

    private fun throwMonthRangeException(): Nothing {
        throw BusinessLogicException(400, "The Range of Month MUST BE BETWEEN 1 and 12")
    }

    private fun throwDayRangeException(max: Int): Nothing {
        throw BusinessLogicException(400, "The Range of Month MUST BE BETWEEN 1 and $max")
    }
}
