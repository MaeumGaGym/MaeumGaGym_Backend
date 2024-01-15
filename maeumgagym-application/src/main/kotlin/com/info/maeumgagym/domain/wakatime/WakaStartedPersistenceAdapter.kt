package com.info.maeumgagym.domain.wakatime

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.wakatime.mapper.WakaMapper
import com.info.maeumgagym.domain.wakatime.repository.WakaStartedRepository
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.wakatime.model.WakaStarted
import com.info.maeumgagym.wakatime.port.out.ExistsWakaStaredByIdPort
import com.info.maeumgagym.wakatime.port.out.ReadWakaStartedFromUser
import com.info.maeumgagym.wakatime.port.out.SaveWakaStartedPort
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

@PersistenceAdapter
class WakaStartedPersistenceAdapter(
    private val wakaStartedRepository: WakaStartedRepository,
    private val wakaMapper: WakaMapper
): SaveWakaStartedPort, ExistsWakaStaredByIdPort, ReadWakaStartedFromUser {

    override fun save(wakaStarted: WakaStarted): WakaStarted {
        return wakaMapper.toDomain(
            wakaStartedRepository.save(wakaMapper.toEntity(wakaStarted))
        )
    }

    override fun existsById(id: UUID): Boolean =
        wakaStartedRepository.existsById(id)

    override fun findByUser(user: User): WakaStarted? =
        wakaStartedRepository.findByIdOrNull(user.id)?.let {
            wakaMapper.toDomain(it)
        }
}
