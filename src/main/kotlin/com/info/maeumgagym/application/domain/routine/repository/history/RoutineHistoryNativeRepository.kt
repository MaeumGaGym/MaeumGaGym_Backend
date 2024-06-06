package com.info.maeumgagym.application.domain.routine.repository.history

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.routine.entity.history.RoutineHistoryJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import java.math.BigInteger
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

    @Query(
        value = "SELECT COUNT(1) " +
            "FROM ${TableNames.ROUTINE_HISTORY_TABLE} r " +
            "WHERE r.origin_id = :originId AND " +
            "r.date = CURDATE() " +
            "LIMIT 1",
        nativeQuery = true
    )
    fun existsByOriginIdAndToday(
        @Param("originId") originId: Long
    ): BigInteger
}
