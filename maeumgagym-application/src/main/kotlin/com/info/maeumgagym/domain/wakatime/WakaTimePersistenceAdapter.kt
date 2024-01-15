package com.info.maeumgagym.domain.wakatime

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.wakatime.mapper.WakaMapper
import com.info.maeumgagym.domain.wakatime.repository.WakaTimeRepository
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.wakatime.model.WakaTime
import com.info.maeumgagym.wakatime.port.out.*
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@PersistenceAdapter
class WakaTimePersistenceAdapter(
    private val wakaTimeRepository: WakaTimeRepository,
    private val wakaMapper: WakaMapper
): SaveWakaTimePort, ReadWakaTimeFromUserAndDatePort {

    override fun save(wakaTime: WakaTime): WakaTime =
        wakaMapper.toDomain(
            wakaTimeRepository.save(wakaMapper.toEntity(wakaTime))
        )

    override fun findByUserAndDate(user: User, date: LocalDate): WakaTime? =
        wakaTimeRepository.findByIdAndDate(user.id, date)?.let {
            wakaMapper.toDomain(it)
        }
}
