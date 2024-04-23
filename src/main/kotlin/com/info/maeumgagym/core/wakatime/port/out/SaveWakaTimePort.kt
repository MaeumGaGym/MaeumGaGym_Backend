package com.info.maeumgagym.core.wakatime.port.out

import com.info.maeumgagym.wakatime.model.WakaTime

interface SaveWakaTimePort {

    fun save(wakaTime: WakaTime): WakaTime
}
