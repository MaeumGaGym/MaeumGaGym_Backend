package com.info.maeumgagym.wakatime.port.out

import com.info.maeumgagym.wakatime.model.WakaTime
import java.time.LocalDate
import java.util.*

interface ReadWakaTimeFromUserAndDatePort {

    fun findByUserIdAndDate(userId: UUID, date: LocalDate): WakaTime?
}
