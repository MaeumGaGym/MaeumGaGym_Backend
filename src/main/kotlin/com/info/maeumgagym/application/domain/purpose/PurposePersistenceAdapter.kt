package com.info.maeumgagym.application.domain.purpose

import com.info.maeumgagym.common.responsibility.PersistenceAdapter
import com.info.maeumgagym.application.domain.purpose.mapper.PurposeMapper
import com.info.maeumgagym.application.domain.purpose.repository.PurposeNativeRepository
import com.info.maeumgagym.application.domain.purpose.repository.PurposeRepository
import com.info.maeumgagym.core.purpose.model.Purpose
import com.info.maeumgagym.core.purpose.port.out.DeletePurposePort
import com.info.maeumgagym.core.purpose.port.out.ReadPurposePort
import com.info.maeumgagym.core.purpose.port.out.SavePurposePort
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@PersistenceAdapter
internal class PurposePersistenceAdapter(
    private val purposeRepository: PurposeRepository,
    private val purposeNativeRepository: PurposeNativeRepository,
    private val purposeMapper: PurposeMapper
) : SavePurposePort,
    ReadPurposePort,
    DeletePurposePort {

    private companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(purpose: Purpose): Purpose =
        purposeMapper.toDomain(
            purposeRepository.save(purposeMapper.toEntity(purpose))
        )

    override fun readById(id: Long): Purpose? =
        purposeRepository.findById(id)?.let {
            purposeMapper.toDomain(it)
        }

    override fun readByUserIdAndDateBetweenOrderByDate(
        userId: UUID,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Purpose> =
        purposeNativeRepository.findAllByUserIdAndDateBetweenOrderByDates(userId, startDate, endDate).map {
            purposeMapper.toDomain(it)
        }

    override fun readByUserIdPaged(userId: UUID, index: Int): List<Purpose> =
        purposeRepository.findAllByUserId(userId, PageRequest.of(index, DEFAULT_PAGE_SIZE)).map {
            purposeMapper.toDomain(it)
        }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun deleteById(id: Long) {
        purposeRepository.deleteById(id)
    }
}
