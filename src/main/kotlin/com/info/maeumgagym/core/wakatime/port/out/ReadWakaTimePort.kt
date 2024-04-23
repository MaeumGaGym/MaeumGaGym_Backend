package com.info.maeumgagym.core.wakatime.port.out

import com.info.maeumgagym.core.wakatime.model.WakaTime
import java.time.LocalDate
import java.util.*

interface ReadWakaTimePort {

    fun readByUserIdAndDate(userId: UUID, date: LocalDate): WakaTime?
}
