package com.info.maeumgagym.domain.wakatime.repository

import com.info.maeumgagym.domain.wakatime.entity.WakaStartJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WakaStartedRepository: JpaRepository<WakaStartJpaEntity, UUID> {
}
