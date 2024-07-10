package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.pose.port.out.ReadPosePort
import com.info.maeumgagym.core.routine.dto.request.UpdateRoutineRequest
import com.info.maeumgagym.core.routine.model.ExerciseInfoModel
import com.info.maeumgagym.core.routine.model.Routine
import com.info.maeumgagym.core.routine.model.RoutineStatusModel
import com.info.maeumgagym.core.routine.port.`in`.UpdateRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.core.routine.port.out.SaveRoutinePort

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

        val poses = req.exerciseInfoRequestList.associate {
            Pair(it.id, readPosePort.readById(it.id) ?: throw BusinessLogicException.POSE_NOT_FOUND)
        }

        req.exerciseInfoRequestList.forEach {
            if (it.weightKilogram != null && poses[it.id]!!.isWeightExercise) {
                throw BusinessLogicException.ONLY_WEIGHT_EXERCISE_CAN_HAVE_WEIGHT_FIELD
            }
        }

        routine.run {
            // 루틴 업데이트
            saveRoutinePort.save(
                Routine(
                    id = id,
                    dayOfWeeks = req.dayOfWeeks,
                    routineStatusModel = RoutineStatusModel(isArchived = req.isArchived, isShared = req.isShared),
                    exerciseInfoModelList = req.exerciseInfoRequestList.map {
                        ExerciseInfoModel(
                            routineId = routine.id,
                            pose = poses[it.id]!!,
                            repetitions = it.repetitions,
                            sets = it.sets,
                            weightKilogram = it.weightKilogram
                        )
                    }.toMutableList(),
                    routineName = req.routineName,
                    userId = userId
                )
            )
        }
    }
}
