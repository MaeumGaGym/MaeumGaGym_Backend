package com.info.maeumgagym.daily.port.out

import com.info.maeumgagym.daily.model.Daily

interface SaveDailyPort {
    fun save(daily: Daily): Daily
}
