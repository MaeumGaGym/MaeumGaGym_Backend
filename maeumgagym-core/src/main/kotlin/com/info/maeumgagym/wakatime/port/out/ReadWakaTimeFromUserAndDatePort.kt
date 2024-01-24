package com.info.maeumgagym.wakatime.port.out

import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.wakatime.model.WakaTime
import java.time.LocalDate

interface ReadWakaTimeFromUserAndDatePort {

    fun findByUserAndDate(user: User, date: LocalDate): WakaTime?
}
