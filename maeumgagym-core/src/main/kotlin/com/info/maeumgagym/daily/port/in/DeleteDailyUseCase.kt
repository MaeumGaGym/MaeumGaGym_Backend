package com.info.maeumgagym.daily.port.`in`

import java.time.LocalDate

interface DeleteDailyUseCase {

    fun delete(date: LocalDate)
}
