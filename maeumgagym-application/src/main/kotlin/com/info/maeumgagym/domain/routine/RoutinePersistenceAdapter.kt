package com.info.maeumgagym.domain.routine

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.routine.mapper.RoutineMapper
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.domain.user.exception.UserNotFoundException
import com.info.maeumgagym.routine.dto.response.RoutineListResponse
import com.info.maeumgagym.routine.dto.response.RoutineResponse
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.port.out.ReadAllRoutineByUserIdPort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort
import com.info.maeumgagym.user.dto.response.UserResponse
import com.info.maeumgagym.user.port.out.FindUserByUUIDPort
import org.springframework.transaction.annotation.Transactional
import java.time.format.TextStyle
import java.util.*

@Transactional
@PersistenceAdapter
class RoutinePersistenceAdapter(
    private val routineMapper: RoutineMapper,
    private val routineRepository: RoutineRepository,
    private val findUserByUUIDPort: FindUserByUUIDPort
) : SaveRoutinePort, ReadAllRoutineByUserIdPort {
    override fun saveRoutine(routine: Routine): Routine {
        val routineJpaEntity = routineRepository.save(routineMapper.toEntity(routine))
        return routineMapper.toDomain(routineJpaEntity)
    }

    override fun readAllRoutineByUserId(userId: UUID): RoutineListResponse {
        val routineEntityList = routineRepository.findAllByUserId(userId)
        val routineDomainList = routineEntityList.map { routineMapper.toDomain(it) }
        val user = findUserByUUIDPort.findUserById(userId) ?: throw UserNotFoundException
        return RoutineListResponse(
            user = UserResponse(
                nickname = user.nickname,
                profileImage = user.profileImage
            ),
            routineList = routineDomainList.map { toDto(it) }
        )
    }

    private fun toDto(routine: Routine) =
        routine.run {
            RoutineResponse(
                id = id!!,
                routineName = routineName,
                exerciseInfoList = exerciseInfoModelList,
                dayOfWeeks = dayOfWeeks?.map { it.getDisplayName(TextStyle.FULL, Locale.KOREA) },
                routineStatus = routineStatusModel
            )
        }
}
