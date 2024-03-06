package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort

@UseCase
internal class CreateRoutineService(
    private val saveRoutinePort: SaveRoutinePort,
    private val readRoutinePort: ReadRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreateRoutineUseCase {

    override fun createRoutine(req: CreateRoutineRequest) {
        val user = readCurrentUserPort.readCurrentUser()

        req.dayOfWeeks?.forEach {
            if (readRoutinePort.readByUserIdAndDayOfWeekAndIsArchivedFalse(user.id!!, it) != null) {
                throw BusinessLogicException.OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK
            }
        }

        req.run {
            // 루틴 저장
            saveRoutinePort.save(
                Routine(
                    routineName = routineName,
                    exerciseInfoModelList = exerciseInfoModelList,
                    dayOfWeeks = dayOfWeeks,
                    routineStatusModel = RoutineStatusModel(
                        isArchived = isArchived,
                        isShared = isShared
                    ),
                    userId = user.id!!
                )
            )
        }
    }
}
