package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.dto.response.CompletableRoutineListResponse
import com.info.maeumgagym.core.routine.port.`in`.ReadAllMyRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ExistsRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort

@ReadOnlyUseCase
internal class ReadMyAllRoutineService(
    private val readRoutinePort: ReadRoutinePort,
    private val existsRoutineHistoryPort: ExistsRoutineHistoryPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadAllMyRoutineUseCase {

    override fun readAllMyRoutine(index: Int): CompletableRoutineListResponse {
        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // 내 루틴 리스트 불러오기
        val routineList = readRoutinePort.readAllByUserIdPaged(user.id!!, index)

        // 반환
        return CompletableRoutineListResponse(
            // 루틴 리스트 돌면서 List<RoutineResponse>로 매핑
            routineList = routineList.map {
                it.toResponse(
                    isCompleted = existsRoutineHistoryPort.existByOriginIdToday(it.id!!)
                )
            }
        )
    }
}
