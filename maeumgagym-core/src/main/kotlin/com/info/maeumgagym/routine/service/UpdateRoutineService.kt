package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest
import com.info.maeumgagym.routine.exception.RoutineNotFoundException
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.`in`.UpdateRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutineByIdPort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort

@UseCase
class UpdateRoutineService(
    private val readRoutineByIdPort: ReadRoutineByIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveRoutinePort: SaveRoutinePort
) : UpdateRoutineUseCase {
    override fun updateRoutine(req: UpdateRoutineRequest, routineId: Long): Routine {
        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // (routine.id = routineId)인 루틴 찾기, 없다면 -> 예외처리
        var routine = readRoutineByIdPort.readRoutineById(routineId) ?: throw RoutineNotFoundException
        if (user.id.compareTo(routine.userId) != 0) {
            throw PermissionDeniedException
        }

        routine = routine.run {
            saveRoutinePort.saveRoutine(
                Routine(
                    id = this.id,
                    dayOfWeeks = req.dayOfWeeks,
                    routineStatusModel = RoutineStatusModel(isArchived = req.isArchived, isShared = req.isShared),
                    exerciseInfoModelList = req.exerciseInfoModelList,
                    routineName = req.routineName,
                    userId = user.id
                )
            )
        }
        return routine
    }
}
