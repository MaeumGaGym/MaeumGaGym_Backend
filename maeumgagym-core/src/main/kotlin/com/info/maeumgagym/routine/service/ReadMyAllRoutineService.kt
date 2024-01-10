package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.ExerciseInfoDto
import com.info.maeumgagym.routine.dto.RoutineStatusDto
import com.info.maeumgagym.routine.dto.response.RoutineListResponse
import com.info.maeumgagym.routine.dto.response.RoutineResponse
import com.info.maeumgagym.routine.port.`in`.ReadAllMyRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadAllRoutineByUserIdPort
import com.info.maeumgagym.user.dto.response.UserResponse
import java.time.format.TextStyle
import java.util.*

@UseCase
class ReadMyAllRoutineService(
    private val readAllRoutineByUserIdPort: ReadAllRoutineByUserIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadAllMyRoutineUseCase {
    override fun readAllMyRoutine(): RoutineListResponse {
        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // 내 루틴 라스트 불러오기
        val routineList = readAllRoutineByUserIdPort.readAllRoutineByUserId(user.id)

        // 반환
        return RoutineListResponse(
            // 루틴 리스트 돌면서 List<RoutineResponse>로 매핑
            routineList = routineList.map {
                RoutineResponse(
                    id = it.id!!,
                    routineName = it.routineName,
                    exerciseInfoList = it.exerciseInfoModelList.map { exerciseInfoModel ->
                        ExerciseInfoDto(
                            exerciseName = exerciseInfoModel.exerciseName,
                            repetitions = exerciseInfoModel.repetitions,
                            sets = exerciseInfoModel.sets
                        )
                    },
                    dayOfWeeks = it.dayOfWeeks?.map { dayOfWeek ->
                        dayOfWeek.getDisplayName(
                            TextStyle.FULL,
                            Locale.KOREA
                        )
                    },
                    routineStatus = it.routineStatusModel.run {
                        RoutineStatusDto(
                            isArchived = isArchived,
                            isShared = isShared
                        )
                    }
                )
            },
            // 유저 정보
            userInfo = UserResponse(nickname = user.nickname, profileImage = user.profileImage)
        )
    }
}
