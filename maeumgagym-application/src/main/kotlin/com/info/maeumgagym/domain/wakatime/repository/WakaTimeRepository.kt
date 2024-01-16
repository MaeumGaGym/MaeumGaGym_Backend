package com.info.maeumgagym.domain.wakatime.repository

import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WakaTimeRepository: JpaRepository<WakaTimeJpaEntity, WakaTimeJpaEntity.IdClass> {
}
