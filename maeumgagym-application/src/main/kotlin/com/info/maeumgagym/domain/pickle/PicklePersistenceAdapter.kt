package com.info.maeumgagym.domain.pickle

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.mapper.PickleMapper
import com.info.maeumgagym.domain.pickle.repository.PickleNativeRepository
import com.info.maeumgagym.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.port.out.*
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
    override fun save(pickle: Pickle): Pickle =
        pickleMapper.toDomain(
            pickleRepository.save(pickleMapper.toEntity(pickle))
        )

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(pickle: Pickle) {
        pickleRepository.delete(pickleMapper.toEntity(pickle))
    }

    override fun readById(id: String): Pickle? =
        pickleRepository.findByVideoId(id)?.let {
            pickleMapper.toDomain(it)
        }

    override fun readAll(): List<Pickle> =
        pickleRepository.findAll().map { pickleMapper.toDomain(it) }

    override fun readAllPagedByTagsContaining(
        simpleTag: String,
        exactTag: String,
        idx: Int,
        size: Int
    ): MutableList<Pickle> = prickleNativeRepository.findByTagsContaining(
        simpleTag,
        exactTag,
        PageRequest.of(idx, size, Sort.by("created_at").descending())
    ).map { pickleMapper.toDomain(it) }.toMutableList()

    override fun existsById(videoId: String): Boolean =
        pickleRepository.findByVideoId(videoId)?.let { true } ?: false
}
