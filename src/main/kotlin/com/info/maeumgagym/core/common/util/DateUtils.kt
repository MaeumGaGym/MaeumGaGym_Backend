package com.info.maeumgagym.core.common.util

import com.info.maeumgagym.core.common.exception.BusinessLogicException
import java.time.LocalDate

object DateUtils {

    fun isPassableDay(year: Int, month: Int, day: Int): LocalDate {
        val days = when (month) {
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }

        if (days > day) throw BusinessLogicException(400, "Invalid Date")

        return LocalDate.of(year, month, day)
    }
}
