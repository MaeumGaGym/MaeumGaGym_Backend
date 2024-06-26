package com.info.maeumgagym.core.purpose.port.out

import com.info.maeumgagym.core.purpose.model.Purpose
import java.time.LocalDate
import java.util.*

interface ReadPurposePort {

    fun readById(id: Long): Purpose?

    fun readByUserIdAndDateBetweenOrderByDate(userId: UUID, startDate: LocalDate, endDate: LocalDate): List<Purpose>

    fun readByUserIdPaged(userId: UUID, index: Int): List<Purpose>
}
