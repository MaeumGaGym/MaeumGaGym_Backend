package com.info.maeumgagym.domain.wakatime.repository

import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface WakaTimeRepository: Repository<WakaTimeJpaEntity, WakaTimeJpaEntity.IdClass> {

    fun findById(id: WakaTimeJpaEntity.IdClass): WakaTimeJpaEntity?

    fun save(entity: WakaTimeJpaEntity): WakaTimeJpaEntity
}
