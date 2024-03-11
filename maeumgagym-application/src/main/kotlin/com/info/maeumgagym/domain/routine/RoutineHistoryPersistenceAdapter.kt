package com.info.maeumgagym.domain.routine

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.routine.mapper.RoutineHistoryMapper
import com.info.maeumgagym.domain.routine.repository.RoutineHistoryRepository
import com.info.maeumgagym.routine.model.RoutineHistory
import com.info.maeumgagym.routine.port.out.*
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@PersistenceAdapter
internal class RoutineHistoryPersistenceAdapter(
    private val mapper: RoutineHistoryMapper,
    private val routineHistoryRepository: RoutineHistoryRepository
) : SaveRoutineHistoryPort, ReadRoutineHistoryPort, ExistsRoutineHistoryPort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(routineHistory: RoutineHistory): RoutineHistory =
        routineHistory.run {
            mapper.toDomain(routineHistoryRepository.save(mapper.toEntity(this)))
        }

    override fun readByUserIdAndDate(userId: UUID, date: LocalDate): RoutineHistory? =
        routineHistoryRepository.findByUserIdAndDate(userId, date)?.run {
            mapper.toDomain(this)
        }

    override fun exsitsByUserIdAndDate(userId: UUID, date: LocalDate): Boolean =
        routineHistoryRepository.findByUserIdAndDate(userId, date) != null
}
