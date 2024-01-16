package com.info.maeumgagym.domain.wakatime

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import com.info.maeumgagym.domain.wakatime.mapper.WakaTimeMapper
import com.info.maeumgagym.domain.wakatime.repository.WakaTimeRepository
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.wakatime.model.WakaTime
import com.info.maeumgagym.wakatime.port.out.*
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@PersistenceAdapter
class WakaTimePersistenceAdapter(
    private val wakaTimeRepository: WakaTimeRepository,
    private val wakaTimeMapper: WakaTimeMapper
): SaveWakaTimePort, ReadWakaTimeFromUserAndDatePort {

    override fun save(wakaTime: WakaTime): WakaTime =
        wakaTimeMapper.toDomain(
            wakaTimeRepository.save(wakaTimeMapper.toEntity(wakaTime))
        )

    override fun findByUserAndDate(user: User, date: LocalDate): WakaTime? =
        wakaTimeRepository.findByIdOrNull(
            WakaTimeJpaEntity.IdClass(user.id, date)
        )?.let {
            wakaTimeMapper.toDomain(it)
        }
}
