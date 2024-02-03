package com.info.maeumgagym.domain.routine.repository

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import java.time.DayOfWeek
import java.util.*

@org.springframework.stereotype.Repository
interface RoutineNativeRepository : Repository<RoutineJpaEntity, Long?> {


    @Query(
        value = "SELECT * FROM ${TableNames.ROUTINE_TABLE} r " +
            "INNER JOIN ${TableNames.ROUTINE_TABLE}_day_of_weeks d " +
            "ON r.id = d.${TableNames.ROUTINE_TABLE}_id " +
            "WHERE r.user_id = :userId AND d.day_of_weeks LIKE :dayOfWeek",
        nativeQuery = true
    )
    fun findByUserIdAndDayOfWeeks(
        @Param("userId") userId: UUID,
        @Param("dayOfWeek") dayOfWeek: String
    ): RoutineJpaEntity?
}
