package com.info.maeumgagym.core.daily.port.out

import com.info.maeumgagym.core.daily.model.Daily
import com.info.maeumgagym.core.user.model.User
import java.time.LocalDate

interface ReadDailyPort {

    fun readByUploaderAndDate(user: User, date: LocalDate): Daily?

    fun readAllByUploader(user: User, page: Int, size: Int): List<Daily>
}
