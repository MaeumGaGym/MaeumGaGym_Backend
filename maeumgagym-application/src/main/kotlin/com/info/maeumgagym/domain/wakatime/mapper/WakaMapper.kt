package com.info.maeumgagym.domain.wakatime.mapper

import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.wakatime.entity.WakaStartJpaEntity
import com.info.maeumgagym.wakatime.model.WakaStarted
import org.springframework.stereotype.Component

@Component
class WakaMapper(
    private val userMapper: UserMapper
) {

    fun toEntity(domain: WakaStarted): WakaStartJpaEntity =
        domain.run {
            WakaStartJpaEntity(
                user = userMapper.toEntity(user),
                startAt = startAt
            )
        }

    fun toDomain(entity: WakaStartJpaEntity): WakaStarted =
        entity.run {
            WakaStarted(
                user = userMapper.toDomain(user),
                startAt = startAt
            )
        }
}
