package com.info.maeumgagym.wakatime.port.out

import com.info.maeumgagym.wakatime.model.WakaStarted

interface SaveWakaStartedPort {
    fun save(wakaStarted: WakaStarted): WakaStarted
}
