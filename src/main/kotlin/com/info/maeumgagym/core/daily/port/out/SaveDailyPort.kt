package com.info.maeumgagym.core.daily.port.out

import com.info.maeumgagym.core.daily.model.Daily

interface SaveDailyPort {
    fun save(daily: Daily): Daily
}
