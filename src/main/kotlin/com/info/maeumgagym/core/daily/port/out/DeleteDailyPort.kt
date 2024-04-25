package com.info.maeumgagym.core.daily.port.out

import com.info.maeumgagym.core.daily.model.Daily

interface DeleteDailyPort {
    fun delete(daily: Daily)
}
