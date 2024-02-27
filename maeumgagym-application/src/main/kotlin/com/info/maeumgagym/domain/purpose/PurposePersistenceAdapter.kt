package com.info.maeumgagym.domain.purpose

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.purpose.mapper.PurposeMapper
import com.info.maeumgagym.domain.purpose.repository.PurposeRepository
import com.info.maeumgagym.purpose.model.Purpose
import com.info.maeumgagym.purpose.port.out.DeletePurposePort
import com.info.maeumgagym.purpose.port.out.ReadPurposePort
import com.info.maeumgagym.purpose.port.out.SavePurposePort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
@PersistenceAdapter
internal class PurposePersistenceAdapter(
    private val purposeRepository: PurposeRepository,
    private val purposeMapper: PurposeMapper
) : SavePurposePort, ReadPurposePort, DeletePurposePort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(purpose: Purpose): Purpose =
        purposeMapper.toDomain(
            purposeRepository.save(purposeMapper.toEntity(purpose))
        )

    override fun readById(id: Long): Purpose? =
        purposeRepository.findById(id)?.let {
            purposeMapper.toDomain(it)
        }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun deleteById(id: Long) {
        purposeRepository.deleteById(id)
    }
}
