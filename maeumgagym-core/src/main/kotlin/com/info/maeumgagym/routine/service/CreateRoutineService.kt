package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.out.SaveRoutinePort

@UseCase
class CreateRoutineService(
    private val saveRoutinePort: SaveRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreateRoutineUseCase {
    override fun createRoutine(createRoutineRequest: CreateRoutineRequest) {
        createRoutineRequest.run {
            saveRoutinePort.saveRoutine(
                Routine(
                    routineName = routineName,
                    exerciseInfoModelList = exerciseInfoModelList,
                    dayOfWeeks = dayOfWeeks,
                    routineStatusModel = RoutineStatusModel(
                        isArchived = isArchived,
                        isShared = isShared
                    ),
                    userId = readCurrentUserPort.readCurrentUser().id
                )
            )
        }
    }
}
