package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.pose.port.out.ReadPosePort
import com.info.maeumgagym.core.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.core.routine.model.ExerciseInfoModel
import com.info.maeumgagym.core.routine.model.Routine
import com.info.maeumgagym.core.routine.model.RoutineStatusModel
import com.info.maeumgagym.core.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.SaveRoutinePort

@UseCase
internal class CreateRoutineService(
    private val saveRoutinePort: SaveRoutinePort,
    private val readPosePort: ReadPosePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreateRoutineUseCase {

    override fun createRoutine(req: CreateRoutineRequest): LocationSubjectDto {
        val user = readCurrentUserPort.readCurrentUser()

        val poses = req.exerciseInfoRequestList.associate {
            Pair(it.id, readPosePort.readById(it.id) ?: throw BusinessLogicException.POSE_NOT_FOUND)
        }

        req.exerciseInfoRequestList.forEach {
            if (it.weightKilogram != null && !poses[it.id]!!.isWeightExercise) {
                throw BusinessLogicException.ONLY_WEIGHT_EXERCISE_CAN_HAVE_WEIGHT_FIELD
            }
        }

        val routine = req.run {
            // 루틴 저장
            saveRoutinePort.save(
                Routine(
                    routineName = routineName,
                    exerciseInfoModelList = exerciseInfoRequestList.map {
                        ExerciseInfoModel(
                            pose = poses[it.id]!!,
                            repetitions = it.repetitions,
                            sets = it.sets,
                            weightKilogram = it.weightKilogram
                        )
                    }.toMutableList(),
                    dayOfWeeks = dayOfWeeks,
                    routineStatusModel = RoutineStatusModel(
                        isArchived = isArchived,
                        isShared = isShared
                    ),
                    userId = user.id!!
                )
            )
        }

        return LocationSubjectDto(routine.id!!)
    }
}
