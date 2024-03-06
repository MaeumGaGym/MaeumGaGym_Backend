package com.info.maeumgagym.daily.port.`in`

import java.time.LocalDate

interface UpdateDailyUseCase {
    fun updateTitle(title: String, date: LocalDate)
}
