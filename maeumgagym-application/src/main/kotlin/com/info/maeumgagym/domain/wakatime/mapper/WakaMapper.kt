package com.info.maeumgagym.domain.wakatime.mapper

import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.wakatime.entity.WakaStartedJpaEntity
import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import com.info.maeumgagym.wakatime.model.WakaStarted
import com.info.maeumgagym.wakatime.model.WakaTime
import org.springframework.stereotype.Component

@Component
class WakaMapper(
    private val userMapper: UserMapper
) {

    fun toEntity(domain: WakaTime): WakaTimeJpaEntity =
        domain.run {
            WakaTimeJpaEntity(
                user = userMapper.toEntity(user),
                id = id,
                date = date,
                waka = waka
            )
        }

    fun toDomain(entity: WakaTimeJpaEntity): WakaTime =
        entity.run {
            WakaTime(
                user = userMapper.toDomain(user),
                id = id,
                date = date,
                waka = waka
            )
        }

    fun toEntity(domain: WakaStarted): WakaStartedJpaEntity =
        domain.run {
            WakaStartedJpaEntity(
                user = userMapper.toEntity(user),
                startAt = startAt,
                id = id
            )
        }

    fun toDomain(entity: WakaStartedJpaEntity): WakaStarted =
        entity.run {
            WakaStarted(
                user = userMapper.toDomain(user),
                startAt = startAt,
                id = id
            )
        }
}
