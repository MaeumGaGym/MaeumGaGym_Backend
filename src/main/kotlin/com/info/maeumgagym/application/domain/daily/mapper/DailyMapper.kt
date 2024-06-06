package com.info.maeumgagym.application.domain.daily.mapper

import com.info.maeumgagym.application.domain.daily.entity.DailyJpaEntity
import com.info.maeumgagym.application.domain.user.mapper.UserMapper
import com.info.maeumgagym.core.daily.model.Daily
import org.springframework.stereotype.Component

@Component
class DailyMapper(
    private val userMapper: UserMapper
) {

    fun toEntity(domain: Daily) = domain.run {
        DailyJpaEntity(
            id = id,
            title = title,
            uploader = userMapper.toEntity(uploader),
            date = date,
            time = time
        )
    }

    fun toDomain(entity: DailyJpaEntity) = entity.run {
        Daily(
            id = id,
            title = title,
            uploader = userMapper.toDomain(uploader),
            date = date,
            time = time
        )
    }
}
