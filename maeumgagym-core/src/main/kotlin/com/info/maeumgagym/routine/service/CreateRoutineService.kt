package com.info.maeumgagym.routine.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.pose.port.out.ReadPosePort
import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import com.info.maeumgagym.routine.port.`in`.CreateRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort

@UseCase
internal class CreateRoutineService(
    private val saveRoutinePort: SaveRoutinePort,
    private val readRoutinePort: ReadRoutinePort,
    private val readPosePort: ReadPosePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreateRoutineUseCase {

    override fun createRoutine(req: CreateRoutineRequest): LocationSubjectDto {
        val user = readCurrentUserPort.readCurrentUser()

        // 이미 사용 중인 루틴들과 겹치는 요일이 있는지 확인하는 과정
        if (!req.isArchived) { // 추가하려는 루틴이 사용되지 않을 예정이라면 이 과정에서 제외
            req.dayOfWeeks?.forEach {
                if (readRoutinePort.readByUserIdAndDayOfWeekAndIsArchivedFalse(user.id!!, it) != null) {
                    throw BusinessLogicException.OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK
                }
            }
        }

        val poses = req.exerciseInfoRequestList.associate {
            Pair(it.id, readPosePort.readById(it.id) ?: throw BusinessLogicException.POSE_NOT_FOUND)
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
                            sets = it.sets
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
