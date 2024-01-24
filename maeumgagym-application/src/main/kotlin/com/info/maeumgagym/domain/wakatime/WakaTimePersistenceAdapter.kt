package com.info.maeumgagym.domain.wakatime

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.wakatime.mapper.WakaTimeMapper
import com.info.maeumgagym.domain.wakatime.repository.WakaTimeRepository
import com.info.maeumgagym.wakatime.model.WakaTime
import com.info.maeumgagym.wakatime.port.out.*
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
    override fun saveAndFlush(wakaTime: WakaTime): WakaTime =
        wakaTimeMapper.toDomain(
            wakaTimeRepository.saveAndFlush(wakaTimeMapper.toEntity(wakaTime))
        )

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(wakaTime: WakaTime): WakaTime =
        wakaTimeMapper.toDomain(
            wakaTimeRepository.save(wakaTimeMapper.toEntity(wakaTime))
        )

    override fun findByUserIdAndDate(userId: UUID, date: LocalDate): WakaTime? =
        wakaTimeRepository.findByUserIdAndDate(userId, date)?.let {
            wakaTimeMapper.toDomain(it)
        }
}
