package com.info.maeumgagym.application.domain.wakatime

import com.info.maeumgagym.common.annotation.responsibility.PersistenceAdapter
import com.info.maeumgagym.application.domain.wakatime.mapper.WakaTimeMapper
import com.info.maeumgagym.application.domain.wakatime.repository.WakaTimeRepository
import com.info.maeumgagym.core.wakatime.model.WakaTime
import com.info.maeumgagym.core.wakatime.port.out.ReadWakaTimePort
import com.info.maeumgagym.core.wakatime.port.out.SaveWakaTimePort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@PersistenceAdapter
internal class WakaTimePersistenceAdapter(
    private val wakaTimeRepository: WakaTimeRepository,
    private val wakaTimeMapper: WakaTimeMapper
) : SaveWakaTimePort, ReadWakaTimePort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(wakaTime: WakaTime): WakaTime =
        wakaTimeMapper.toDomain(
            wakaTimeRepository.save(wakaTimeMapper.toEntity(wakaTime))
        )

    override fun readByUserIdAndDate(userId: UUID, date: LocalDate): WakaTime? =
        wakaTimeRepository.findByUserIdAndDate(userId, date)?.let {
            wakaTimeMapper.toDomain(it)
        }
}
