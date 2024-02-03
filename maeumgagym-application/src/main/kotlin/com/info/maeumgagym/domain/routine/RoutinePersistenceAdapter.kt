package com.info.maeumgagym.domain.routine

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.routine.mapper.RoutineMapper
import com.info.maeumgagym.domain.routine.repository.RoutineNativeRepository
import com.info.maeumgagym.domain.routine.repository.RoutineRepository
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.port.out.DeleteRoutinePort
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.routine.port.out.SaveRoutinePort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.util.*

@PersistenceAdapter
internal class RoutinePersistenceAdapter(
    private val routineMapper: RoutineMapper,
    private val routineRepository: RoutineRepository,
    private val routineNativeRepository: RoutineNativeRepository
) : SaveRoutinePort, ReadRoutinePort, DeleteRoutinePort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(routine: Routine): Routine {
        val routineJpaEntity = routineRepository.save(routineMapper.toEntity(routine))
        return routineMapper.toDomain(routineJpaEntity)
    }

    override fun readById(routineId: Long): Routine? =
        routineRepository.findById(routineId)?.let { routineMapper.toDomain(it) }

    override fun readAllByUserId(userId: UUID): List<Routine> {
        val routineEntityList = routineRepository.findAllByUserId(userId)
        return routineEntityList.map { routineMapper.toDomain(it) }
    }

    override fun readByUserIdAndDayOfWeekAndIsArchivedFalse(userId: UUID, dayOfWeek: DayOfWeek): Routine? =
        routineNativeRepository.findByUserIdAndDayOfWeekAndIsArchivedFalse(userId, dayOfWeek.name)?.run {
            routineMapper.toDomain(this)
        }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(routine: Routine) =
        routineRepository.delete(routineMapper.toEntity(routine))
}
