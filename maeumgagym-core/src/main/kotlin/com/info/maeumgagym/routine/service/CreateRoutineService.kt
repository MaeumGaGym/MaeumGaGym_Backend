package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.exception.OtherRoutineAlreadyUsingAtDayOfWeekException
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class CreateRoutineService(
    private val saveRoutinePort: SaveRoutinePort,
    private val readRoutinePort: ReadRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreateRoutineUseCase {

    override fun createRoutine(req: CreateRoutineRequest) {
        val user = readCurrentUserPort.readCurrentUser()

        req.dayOfWeeks?.forEach {
            if (readRoutinePort.readByUserIdAndDayOfWeekAndIsArchivedFalse(user.id!!, it) != null) {
                throw OtherRoutineAlreadyUsingAtDayOfWeekException
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
