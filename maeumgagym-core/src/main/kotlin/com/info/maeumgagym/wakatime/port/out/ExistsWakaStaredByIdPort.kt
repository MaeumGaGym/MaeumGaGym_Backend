package com.info.maeumgagym.wakatime.port.out

import java.util.*

interface ExistsWakaStaredByIdPort {

    fun existsById(id: UUID): Boolean
}
