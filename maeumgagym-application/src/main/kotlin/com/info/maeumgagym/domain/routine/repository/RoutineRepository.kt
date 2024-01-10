package com.info.maeumgagym.domain.routine.repository

import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoutineRepository : JpaRepository<RoutineJpaEntity, Long> {
    fun findAllByUserId(userId: UUID): List<RoutineJpaEntity>
}
