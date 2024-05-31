package com.info.maeumgagym.application.domain.routine

import com.info.maeumgagym.application.domain.routine.mapper.ExerciseInfoHistoryListMapper
import com.info.maeumgagym.application.domain.routine.mapper.RoutineHistoryMapper
import com.info.maeumgagym.application.domain.routine.repository.history.ExerciseInfoHistoryRepository
import com.info.maeumgagym.application.domain.routine.repository.history.RoutineHistoryNativeRepository
import com.info.maeumgagym.application.domain.routine.repository.history.RoutineHistoryRepository
import com.info.maeumgagym.common.annotation.responsibility.PersistenceAdapter
import com.info.maeumgagym.core.routine.model.RoutineHistory
import com.info.maeumgagym.core.routine.port.out.ExistsRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.SaveRoutineHistoryPort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@PersistenceAdapter
internal class RoutineHistoryPersistenceAdapter(
    private val mapper: RoutineHistoryMapper,
    private val exerciseInfoHistoryListMapper: ExerciseInfoHistoryListMapper,
    private val routineHistoryRepository: RoutineHistoryRepository,
    private val routineHistoryNativeRepository: RoutineHistoryNativeRepository,
    private val exerciseInfoHistoryRepository: ExerciseInfoHistoryRepository
) : SaveRoutineHistoryPort,
    ReadRoutineHistoryPort,
    ExistsRoutineHistoryPort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(routineHistory: RoutineHistory): RoutineHistory {
        val saved = routineHistoryRepository.save(mapper.toEntity(routineHistory))

        exerciseInfoHistoryListMapper.toEntityList(routineHistory.exerciseInfoHistoryList, saved.id!!).map {
            exerciseInfoHistoryRepository.save(it)
        }

        val savedExerciseInfoHistories = exerciseInfoHistoryRepository.findAllByRoutineHistoryId(saved.id)

        return mapper.toDomain(saved, savedExerciseInfoHistories)
    }

    override fun readByUserIdAndDateBetweenOrderByDate(
        userId: UUID,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<RoutineHistory> =
        routineHistoryNativeRepository
            .findAllByUserIdAndDateBetweenOrderByDateAsc(userId, startDate, endDate).map {
                mapper.toDomain(
                    it,
                    exerciseInfoHistoryRepository.findAllByRoutineHistoryId(it.id!!)
                )
            }

    override fun exsitsByUserIdAndDate(userId: UUID, date: LocalDate): Boolean =
        routineHistoryRepository.findByUserIdAndDate(userId, date) != null

    override fun existByOriginIdToday(
        originId: Long
    ): Boolean = routineHistoryNativeRepository.existsByOriginIdAndToday(originId)
}
