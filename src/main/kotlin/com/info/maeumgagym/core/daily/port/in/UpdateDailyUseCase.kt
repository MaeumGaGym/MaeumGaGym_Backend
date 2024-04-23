package com.info.maeumgagym.core.daily.port.`in`

import java.time.LocalDate

interface UpdateDailyUseCase {
    fun updateTitle(title: String, date: LocalDate)
}
