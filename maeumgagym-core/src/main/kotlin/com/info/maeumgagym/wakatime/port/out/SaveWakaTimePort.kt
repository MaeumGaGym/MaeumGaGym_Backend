package com.info.maeumgagym.wakatime.port.out

import com.info.maeumgagym.wakatime.model.WakaTime

interface SaveWakaTimePort {

    fun save(wakaTime: WakaTime): WakaTime
}
