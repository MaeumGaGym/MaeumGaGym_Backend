package com.info.maeumgagym.domain.routine.repository

import com.info.maeumgagym.domain.routine.entity.RoutineHistoryJpaEntity
import org.springframework.data.repository.Repository
import java.time.LocalDate
import java.util.*

@org.springframework.stereotype.Repository
interface RoutineHistoryRepository : Repository<RoutineHistoryJpaEntity, Long> {

    fun save(entity: RoutineHistoryJpaEntity): RoutineHistoryJpaEntity

    fun findByUserIdAndDate(userId: UUID, date: LocalDate): RoutineHistoryJpaEntity?
}
