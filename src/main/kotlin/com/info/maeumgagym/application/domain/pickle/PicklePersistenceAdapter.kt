package com.info.maeumgagym.application.domain.pickle

import com.info.maeumgagym.common.annotation.responsibility.PersistenceAdapter
import com.info.maeumgagym.application.domain.pickle.mapper.PickleMapper
import com.info.maeumgagym.application.domain.pickle.repository.PickleNativeRepository
import com.info.maeumgagym.application.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.core.pickle.port.out.DeletePicklePort
import com.info.maeumgagym.core.pickle.port.out.ExistsPicklePort
import com.info.maeumgagym.core.pickle.port.out.ReadPicklePort
import com.info.maeumgagym.core.pickle.port.out.SavePicklePort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@PersistenceAdapter
internal class PicklePersistenceAdapter(
    private val prickleNativeRepository: PickleNativeRepository,
    private val pickleRepository: PickleRepository,
    private val pickleMapper: PickleMapper
) : SavePicklePort, DeletePicklePort, ReadPicklePort, ExistsPicklePort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(pickle: com.info.maeumgagym.core.pickle.model.Pickle): com.info.maeumgagym.core.pickle.model.Pickle =
        pickleMapper.toDomain(
            pickleRepository.save(pickleMapper.toEntity(pickle))
        )

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(pickle: com.info.maeumgagym.core.pickle.model.Pickle) {
        pickleRepository.delete(pickleMapper.toEntity(pickle))
    }

    override fun readById(id: String): com.info.maeumgagym.core.pickle.model.Pickle? =
        pickleRepository.findById(id)?.let {
            pickleMapper.toDomain(it)
        }

    override fun readAll(): List<com.info.maeumgagym.core.pickle.model.Pickle> =
        pickleRepository.findAll().map { pickleMapper.toDomain(it) }

    override fun readAllPagedByTagsContaining(
        simpleTag: String,
        exactTag: String,
        idx: Int,
        size: Int
    ): MutableList<com.info.maeumgagym.core.pickle.model.Pickle> = prickleNativeRepository.findByTagsContaining(
        simpleTag,
        exactTag,
        PageRequest.of(idx, size, Sort.by("created_at").descending())
    ).map { pickleMapper.toDomain(it) }.toMutableList()

    override fun existsById(videoId: String): Boolean =
        pickleRepository.findById(videoId)?.let { true } ?: false
}
