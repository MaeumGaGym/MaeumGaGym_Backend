package com.info.maeumgagym.wakatime.port.out

import com.info.maeumgagym.wakatime.model.WakaTime

interface SaveWakaTimePort {

    fun saveAndFlush(wakaTime: WakaTime): WakaTime

    fun save(wakaTime: WakaTime): WakaTime
}
