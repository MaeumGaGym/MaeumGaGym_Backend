package com.info.maeumgagym.wakatime.port.out

import com.info.maeumgagym.wakatime.model.WakaTime
import java.time.LocalDate
import java.util.*

interface ReadWakaTimePort {

    fun readByUserIdAndDate(userId: UUID, date: LocalDate): WakaTime?
}
