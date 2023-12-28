package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.exception.RoutineNotFoundException
import com.info.maeumgagym.routine.port.`in`.DeleteRoutineUseCase
import com.info.maeumgagym.routine.port.out.DeleteRoutinePort
import com.info.maeumgagym.routine.port.out.ReadRoutineByIdPort
import java.util.*

@UseCase
class DeleteRoutineService(
    private val deleteRoutinePort: DeleteRoutinePort,
    private val readRoutineByIdPort: ReadRoutineByIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort
): DeleteRoutineUseCase {
    override fun deleteRoutine(routineId: UUID) {
        val routine = readRoutineByIdPort.readRoutineById(routineId) ?: throw RoutineNotFoundException
        val user = readCurrentUserPort.readCurrentUser()

        if(user.id != routine.userId) throw PermissionDeniedException

        deleteRoutinePort.deleteRoutine(routine)
    }
}
