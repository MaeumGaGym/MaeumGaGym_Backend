package com.info.maeumgagym.daily.port.out

import java.time.LocalDate

interface DeleteImageObjectPort {

    fun deleteObjects(oauthId: String, date: LocalDate, objectName: String)
}
