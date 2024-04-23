package com.info.maeumgagym.core.purpose.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import java.time.LocalDate

@ReadOnlyUseCase
class ReadPurposesFromDateForRangeService(
    private val readPurposePort: com.info.maeumgagym.core.purpose.port.out.ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : com.info.maeumgagym.core.purpose.port.`in`.ReadPurposesFromDateForRangeUseCase {

    override fun readPurposesFromDateForRange(startDate: LocalDate): com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val endDate = startDate.plusDays(40)

        return com.info.maeumgagym.core.purpose.dto.response.PurposeListResponse(
            readPurposePort.readByUserIdAndDateBetweenOrderByDate(user.id!!, startDate, endDate).map {
                it.toResponse()
            }
        )
    }
}
