package com.info.maeumgagym.domain.wakatime

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.wakatime.mapper.WakaMapper
import com.info.maeumgagym.domain.wakatime.repository.WakaStartedRepository
import com.info.maeumgagym.wakatime.model.WakaStarted
import com.info.maeumgagym.wakatime.port.out.ExistsWakaStaredByIdPort
import com.info.maeumgagym.wakatime.port.out.SaveWakaStartedPort
import java.util.UUID

@PersistenceAdapter
class WakaPersistenceAdapter(
    private val wakaStartedRepository: WakaStartedRepository,
    private val wakaMapper: WakaMapper
): SaveWakaStartedPort, ExistsWakaStaredByIdPort {

    override fun save(wakaStarted: WakaStarted): WakaStarted =
        wakaMapper.toDomain(
            wakaStartedRepository.save(wakaMapper.toEntity(wakaStarted))
        )

    override fun existsById(id: UUID): Boolean =
        wakaStartedRepository.existsById(id)
}
