package com.info.maeumgagym.daily.port.out

import com.info.maeumgagym.daily.model.Daily

interface DeleteDailyPort {
    fun delete(daily: Daily)
}
