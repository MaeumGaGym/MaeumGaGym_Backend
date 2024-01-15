package com.info.maeumgagym.wakatime.port.out

import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.wakatime.model.WakaStarted

interface ReadWakaStartedFromUser {
    fun findByUser(user: User): WakaStarted?
}
