package com.info.maeumgagym.application.domain.routine.repository.current

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.routine.entity.current.RoutineJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param
import java.util.*

@org.springframework.stereotype.Repository
interface RoutineNativeRepository : Repository<RoutineJpaEntity, Long?> {

    @Query(
        value = "SELECT * FROM ${TableNames.ROUTINE_TABLE} r " +
            "INNER JOIN ${TableNames.ROUTINE_TABLE}_day_of_weeks d " +
            "ON r.id = d.${TableNames.ROUTINE_TABLE}_id " +
            "WHERE r.is_archived = false AND r.user_id = :userId AND d.day_of_weeks LIKE :dayOfWeek",
        nativeQuery = true
    )
    fun findByUserIdAndDayOfWeekAndIsArchivedFalse(
        @Param("userId") userId: UUID,
        @Param("dayOfWeek") dayOfWeek: String
    ): List<RoutineJpaEntity>
}
