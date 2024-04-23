package com.info.maeumgagym.application.domain.routine

import com.info.maeumgagym.common.responsibility.PersistenceAdapter
import com.info.maeumgagym.application.domain.routine.mapper.ExerciseInfoListMapper
import com.info.maeumgagym.application.domain.routine.mapper.RoutineMapper
import com.info.maeumgagym.application.domain.routine.repository.current.ExerciseInfoRepository
import com.info.maeumgagym.application.domain.routine.repository.current.RoutineNativeRepository
import com.info.maeumgagym.application.domain.routine.repository.current.RoutineRepository
import com.info.maeumgagym.core.routine.model.Routine
import com.info.maeumgagym.core.routine.port.out.DeleteRoutinePort
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.core.routine.port.out.SaveRoutinePort
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.util.*

@PersistenceAdapter
internal class RoutinePersistenceAdapter(
    private val routineMapper: RoutineMapper,
    private val exerciseInfoListMapper: ExerciseInfoListMapper,
    private val routineRepository: RoutineRepository,
    private val routineNativeRepository: RoutineNativeRepository,
    private val exerciseInfoRepository: ExerciseInfoRepository
) : SaveRoutinePort,
    ReadRoutinePort,
    DeleteRoutinePort {

    private companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(routine: Routine): Routine {
        val routineJpaEntity = routineRepository.save(routineMapper.toEntity(routine))

        if (routine.id == null) {
            exerciseInfoListMapper.toEntityList(
                routine.exerciseInfoModelList,
                routineJpaEntity.id!!
            ).map {
                exerciseInfoRepository.save(it)
            }
        } else {
            updateExerciseInfoEntities(routine)
        }

        return routineMapper.toDomain(
            routineJpaEntity,
            exerciseInfoRepository.findAllByRoutineId(routineJpaEntity.id!!)
        )
    }

    override fun readById(routineId: Long): Routine? {
        val routine = routineRepository.findById(routineId) ?: return null
        val exerciseInfoEntities = exerciseInfoRepository.findAllByRoutineId(routine.id!!)
        return routineMapper.toDomain(routine, exerciseInfoEntities)
    }

    override fun readAllByUserIdPaged(userId: UUID, index: Int): List<Routine> {
        val routineEntityList = routineRepository.findAllByUserId(userId, PageRequest.of(index, DEFAULT_PAGE_SIZE))
        return routineEntityList.map {
            routineMapper.toDomain(
                it,
                exerciseInfoRepository.findAllByRoutineId(it.id!!)
            )
        }
    }

    override fun readByUserIdAndDayOfWeekAndIsArchivedFalse(userId: UUID, dayOfWeek: DayOfWeek): Routine? =
        routineNativeRepository.findByUserIdAndDayOfWeekAndIsArchivedFalse(userId, dayOfWeek.name)?.run {
            routineMapper.toDomain(
                this,
                exerciseInfoRepository.findAllByRoutineId(this.id!!)
            )
        }

    override fun readByUserIdAndDayOfWeek(userId: UUID, dayOfWeek: DayOfWeek): Routine? =
        routineNativeRepository.findByUserIdAndDayOfWeek(userId, dayOfWeek.name)?.run {
            routineMapper.toDomain(
                this,
                exerciseInfoRepository.findAllByRoutineId(this.id!!)
            )
        }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(routine: Routine) {
        routineRepository.delete(routineMapper.toEntity(routine))
        exerciseInfoRepository.deleteAllByRoutineId(routine.id!!)
    }

    private fun updateExerciseInfoEntities(routine: Routine) {
        val needSaveOrDeleteExerciseEntities =
            ExerciseInfoUtils.getEntitiesNeedSaveOrDelete(
                exerciseInfoRepository.findAllByRoutineId(routine.id!!),
                exerciseInfoListMapper.toEntityList(routine.exerciseInfoModelList, routine.id!!)
            )

        needSaveOrDeleteExerciseEntities.first.map {
            exerciseInfoRepository.save(it)
        }
        needSaveOrDeleteExerciseEntities.second.map {
            exerciseInfoRepository.delete(it)
        }
    }
}
