package com.info.maeumgagym.domain.wakatime.repository

import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface WakaTimeRepository: JpaRepository<WakaTimeJpaEntity, UUID> {

    fun findByIdAndDate(id: UUID, date: LocalDate): WakaTimeJpaEntity?
}
