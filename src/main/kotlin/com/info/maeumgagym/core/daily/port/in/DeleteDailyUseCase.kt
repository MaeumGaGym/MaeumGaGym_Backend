package com.info.maeumgagym.core.daily.port.`in`

import java.time.LocalDate

interface DeleteDailyUseCase {

    fun delete(date: LocalDate)
}
