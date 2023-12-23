package com.info.maeumgagym.domain.pickle

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.mapper.PickleMapper
import com.info.maeumgagym.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.port.out.SavePicklePort
import org.springframework.transaction.annotation.Transactional

@Transactional
@PersistenceAdapter
class PicklePersistenceAdapter(
    private val pickleRepository: PickleRepository,
    private val pickleMapper: PickleMapper
) : SavePicklePort {

    override fun savePickle(pickle: Pickle): Pickle = pickleMapper.toDomain(
        pickleRepository.save(pickleMapper.toEntity(pickle))
    )
}
