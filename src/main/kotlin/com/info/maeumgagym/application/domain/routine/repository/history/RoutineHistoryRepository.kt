package com.info.maeumgagym.application.domain.routine.repository.history

import com.info.maeumgagym.application.domain.routine.entity.history.RoutineHistoryJpaEntity
import org.springframework.data.repository.Repository
import java.time.LocalDate
import java.util.*

@org.springframework.stereotype.Repository
interface RoutineHistoryRepository : Repository<RoutineHistoryJpaEntity, Long?> {

    fun save(entity: RoutineHistoryJpaEntity): RoutineHistoryJpaEntity

    fun findByUserIdAndDate(userId: UUID, date: LocalDate): RoutineHistoryJpaEntity?
}
