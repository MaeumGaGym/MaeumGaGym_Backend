package com.info.maeumgagym.daily.port.out

import com.info.maeumgagym.daily.model.Daily
import com.info.maeumgagym.user.model.User
import java.time.LocalDate

interface ReadDailyPort {

    fun readByUploaderAndDate(user: User, date: LocalDate): Daily?

    fun readAllByUploader(user: User, page: Int, size: Int): List<Daily>
}
