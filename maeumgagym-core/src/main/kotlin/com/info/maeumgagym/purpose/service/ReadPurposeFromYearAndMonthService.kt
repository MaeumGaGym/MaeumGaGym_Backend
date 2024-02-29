package com.info.maeumgagym.purpose.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.purpose.dto.response.PurposeListResponse
import com.info.maeumgagym.purpose.port.`in`.ReadPurposesFromYearAndMonthUseCase
import com.info.maeumgagym.purpose.port.out.ReadPurposePort
import java.time.LocalDate

@ReadOnlyUseCase
class ReadPurposeFromYearAndMonthService(
    private val readPurposePort: ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadPurposesFromYearAndMonthUseCase {

    override fun readPurposesFromYearAndMonth(year: Int, month: Int): PurposeListResponse {

        val user = readCurrentUserPort.readCurrentUser()

        // 입력된 값을 통해 LocalDate 객체 생성 (YYYY-MM-01)
        val date = LocalDate.of(year, month, 1)

        // 조회할 Purpose 날짜 범위 중, 시작 날짜 (입력된 날짜로부터 1달 전의 첫번째 날)
        val startDate = date.minusMonths(1)

        // 조회할 Purpose 날짜 범위 중, 끝 날짜 (입력된 날짜로부터 1달 후의 마지막 날)
        val endDate = date.plusMonths(1).run {
            plusDays(lengthOfMonth().toLong())
        }

        // startDate부터 endDate 안에 포함되는 목표들 조회
        return PurposeListResponse(
            readPurposePort.readByUserIdAndDateBetween(user.id!!, startDate, endDate).map {
                it.toResponse()
            }
        )
    }
}
