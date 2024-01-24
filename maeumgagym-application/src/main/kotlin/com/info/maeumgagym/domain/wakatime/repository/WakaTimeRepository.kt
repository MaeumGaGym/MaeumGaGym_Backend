package com.info.maeumgagym.domain.wakatime.repository

import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import org.springframework.data.repository.Repository
import java.time.LocalDate
import java.util.*

@org.springframework.stereotype.Repository
interface WakaTimeRepository : Repository<WakaTimeJpaEntity, UUID?> {

    fun findByUserIdAndDate(userId: UUID, date: LocalDate): WakaTimeJpaEntity?

    fun save(entity: WakaTimeJpaEntity): WakaTimeJpaEntity
}
