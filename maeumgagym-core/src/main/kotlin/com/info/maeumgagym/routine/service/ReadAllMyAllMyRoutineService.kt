package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.response.RoutineListResponse
import com.info.maeumgagym.routine.port.`in`.ReadAllMyRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadAllRoutineByUserIdPort

@UseCase
class ReadAllMyAllMyRoutineService(
    private val readAllRoutineByUserIdPort: ReadAllRoutineByUserIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadAllMyRoutineUseCase {
    override fun readRoutine(): RoutineListResponse {
        val userId = readCurrentUserPort.readCurrentUser().id
        return readAllRoutineByUserIdPort.readAllRoutineByUserId(userId)
    }
}
