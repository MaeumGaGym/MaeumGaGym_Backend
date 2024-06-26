package com.info.maeumgagym.application.domain.routine.repository.current

import com.info.maeumgagym.application.domain.routine.entity.current.RoutineJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.util.*

@org.springframework.stereotype.Repository
interface RoutineRepository : Repository<RoutineJpaEntity, Long> {
    fun save(entity: RoutineJpaEntity): RoutineJpaEntity

    fun findById(id: Long): RoutineJpaEntity?

    fun findAllByUserId(userId: UUID, pageable: Pageable): List<RoutineJpaEntity>

    fun delete(entity: RoutineJpaEntity)
}
