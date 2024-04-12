package com.info.maeumgagym.routine.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.pose.port.out.ReadPosePort
import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.`in`.UpdateRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort

@UseCase
internal class UpdateRoutineService(
    private val readRoutinePort: ReadRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveRoutinePort: SaveRoutinePort,
    private val readPosePort: ReadPosePort
) : UpdateRoutineUseCase {
    override fun updateRoutine(req: UpdateRoutineRequest, routineId: Long) {
        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // (routine.id = routineId)인 루틴 찾기, 없다면 -> 예외처리
        val routine = readRoutinePort.readById(routineId) ?: throw BusinessLogicException.ROUTINE_NOT_FOUND

        // 루틴을 만든 이가 토큰의 유저가 맞는지 검증, 아닐시 -> 예외처리
        if (user.id != routine.userId) throw SecurityException.PERMISSION_DENIED

        // 이미 사용 중인 루틴들과 겹치는 요일이 있는지 확인하는 과정
        if (!req.isArchived) { // 추가하려는 루틴이 사용되지 않을 예정이라면 이 과정에서 제외
            req.dayOfWeeks?.forEach {
                val found = readRoutinePort.readByUserIdAndDayOfWeekAndIsArchivedFalse(user.id, it)

                if (found != null && found.id != routine.id) {
                    throw BusinessLogicException.OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK
                }
            }
        }

        val poses = req.exerciseInfoResponseList.associate {
            Pair(it.id, readPosePort.readById(it.id) ?: throw BusinessLogicException.POSE_NOT_FOUND)
        }

        routine.run {
            // 루틴 업데이트
            saveRoutinePort.save(
                Routine(
                    id = id,
                    dayOfWeeks = req.dayOfWeeks,
                    routineStatusModel = RoutineStatusModel(isArchived = req.isArchived, isShared = req.isShared),
                    exerciseInfoModelList = req.exerciseInfoResponseList.map {
                        ExerciseInfoModel(
                            routineId = routine.id,
                            pose = poses[it.id]!!,
                            repetitions = it.repetitions,
                            sets = it.sets
                        )
                    }.toMutableList(),
                    routineName = req.routineName,
                    userId = userId
                )
            )
        }
    }
}
