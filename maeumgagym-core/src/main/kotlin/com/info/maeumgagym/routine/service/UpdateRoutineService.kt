package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest
import com.info.maeumgagym.routine.exception.OtherRoutineAlreadyUsingAtDayOfWeekException
import com.info.maeumgagym.routine.exception.RoutineNotFoundException
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.`in`.UpdateRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class UpdateRoutineService(
    private val readRoutinePort: ReadRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveRoutinePort: SaveRoutinePort
) : UpdateRoutineUseCase {
    override fun updateRoutine(req: UpdateRoutineRequest, routineId: Long) {
        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // (routine.id = routineId)인 루틴 찾기, 없다면 -> 예외처리
        val routine = readRoutinePort.readById(routineId) ?: throw RoutineNotFoundException

        // 루틴을 만든 이가 토큰의 유저가 맞는지 검증, 아닐시 -> 예외처리
        if (user.id != routine.userId) throw PermissionDeniedException

        req.dayOfWeeks?.forEach {
            if (readRoutinePort.readByUserIdAndDayOfWeek(user.id!!, it) != null) {
                throw OtherRoutineAlreadyUsingAtDayOfWeekException
            }
        }

        routine.run {
            // 루틴 업데이트
            saveRoutinePort.save(
                Routine(
                    id = id,
                    dayOfWeeks = req.dayOfWeeks,
                    routineStatusModel = RoutineStatusModel(isArchived = req.isArchived, isShared = req.isShared),
                    exerciseInfoModelList = req.exerciseInfoModelList,
                    routineName = req.routineName,
                    userId = userId
                )
            )
        }
    }
}
