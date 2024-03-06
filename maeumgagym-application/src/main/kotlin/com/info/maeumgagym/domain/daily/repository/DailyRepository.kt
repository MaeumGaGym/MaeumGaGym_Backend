package com.info.maeumgagym.domain.daily.repository

import com.info.maeumgagym.domain.daily.entity.DailyJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository
import java.time.LocalDate
import java.util.UUID

@org.springframework.stereotype.Repository
interface DailyRepository : Repository<DailyJpaEntity, Long> {

    fun save(entity: DailyJpaEntity): DailyJpaEntity

    fun delete(entity: DailyJpaEntity)

    fun findByUploaderIdAndDate(userId: UUID, date: LocalDate): DailyJpaEntity?

    fun findAllByUploaderId(userId: UUID, pageable: Pageable): List<DailyJpaEntity>
}
