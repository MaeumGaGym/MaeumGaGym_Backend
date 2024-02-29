package com.info.maeumgagym.purpose.port.out

import com.info.maeumgagym.purpose.model.Purpose
import java.time.LocalDate
import java.util.*

interface ReadPurposePort {

    fun readById(id: Long): Purpose?

    fun readByUserIdAndDateBetween(userId: UUID, startDate: LocalDate, endDate: LocalDate): List<Purpose>
}
