package com.info.maeumgagym.domain.routine.repository

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.routine.entity.RoutineHistoryJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.util.*

@org.springframework.stereotype.Repository
interface RoutineHistoryNativeRepository : Repository<RoutineHistoryJpaEntity, Long?> {

    @Query(
        value = "SELECT * " +
            "FROM ${TableNames.ROUTINE_HISTORY_TABLE} r " +
            "WHERE (r.user_id = :userId) AND " +
            ":startDate <= r.date AND r.date <= :endDate " +
            "ORDER BY date",
        nativeQuery = true
    )
    fun findAllByUserIdAndDateBetweenOrderByDateAsc(
        @Param("userId") userId: UUID,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<RoutineHistoryJpaEntity>
}
