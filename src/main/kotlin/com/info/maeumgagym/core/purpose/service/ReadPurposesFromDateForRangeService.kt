package com.info.maeumgagym.core.purpose.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse
import com.info.maeumgagym.core.purpose.port.`in`.ReadPurposesFromDateForRangeUseCase
import com.info.maeumgagym.core.purpose.port.out.ReadPurposePort
import java.time.LocalDate

@ReadOnlyUseCase
class ReadPurposesFromDateForRangeService(
    private val readPurposePort: ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadPurposesFromDateForRangeUseCase {

    override fun readPurposesFromDateForRange(startDate: LocalDate): PurposeListResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val endDate = startDate.plusDays(40)

        return PurposeListResponse(
            readPurposePort.readByUserIdAndDateBetweenOrderByDate(user.id!!, startDate, endDate).map {
                it.toResponse()
            }
        )
    }
}
