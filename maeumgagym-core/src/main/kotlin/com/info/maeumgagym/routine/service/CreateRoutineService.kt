package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.exception.ExerciseListCannotEmptyException
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.out.SaveRoutinePort

@UseCase
internal class CreateRoutineService(
    private val saveRoutinePort: SaveRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreateRoutineUseCase {
    override fun createRoutine(req: CreateRoutineRequest) {
        // 운동 리스트가 비어있다면 -> 예외 처리
        if (req.exerciseInfoModelList.isEmpty()) throw ExerciseListCannotEmptyException

        req.run {
            // 루틴 저장
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
