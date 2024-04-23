package com.info.maeumgagym.core.daily.port.out

import java.time.LocalDate

interface DeleteImageObjectPort {

    fun deleteObjects(oauthId: String, date: LocalDate, objectName: String)
}
