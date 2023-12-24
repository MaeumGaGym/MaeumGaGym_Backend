package com.info.maeumgagym.domain.routine

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.routine.mapper.RoutineMapper
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.port.out.ReadAllRoutineByUserIdPort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort
import com.info.maeumgagym.user.port.out.FindUserByUUIDPort
import org.springframework.transaction.annotation.Transactional
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

    override fun readAllRoutineByUserId(userId: UUID): List<Routine> {
        val routineEntityList = routineRepository.findAllByUserId(userId)
        return routineEntityList.map { routineMapper.toDomain(it) }
    }
}
