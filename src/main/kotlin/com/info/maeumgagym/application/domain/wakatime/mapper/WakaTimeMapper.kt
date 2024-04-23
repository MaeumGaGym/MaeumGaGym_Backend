package com.info.maeumgagym.application.domain.wakatime.mapper

import com.info.maeumgagym.application.domain.user.mapper.UserMapper
import com.info.maeumgagym.application.domain.wakatime.entity.WakaTimeJpaEntity
import com.info.maeumgagym.core.wakatime.model.WakaTime
import org.springframework.stereotype.Component

@Component
class WakaTimeMapper(
    private val userMapper: UserMapper
) {

    fun toEntity(domain: WakaTime): WakaTimeJpaEntity =
        domain.run {
            WakaTimeJpaEntity(
                user = userMapper.toEntity(user),
                date = date,
                waka = waka,
                id = id
            )
        }

    fun toDomain(entity: WakaTimeJpaEntity): WakaTime =
        entity.run {
            WakaTime(
                user = userMapper.toDomain(user),
                date = date,
                waka = waka,
                id = id
            )
        }
}
