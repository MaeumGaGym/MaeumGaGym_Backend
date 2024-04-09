package com.info.maeumgagym.domain.pickle

import com.info.common.responsibility.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.mapper.PickleCommentMapper
import com.info.maeumgagym.domain.pickle.repository.PickleCommentRepository
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.port.out.DeletePickleCommentPort
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentsPort
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.pickle.port.out.SavePickleCommentPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@PersistenceAdapter
internal class PickleCommentPersistenceAdapter(
    private val pickleCommentRepository: PickleCommentRepository,
    private val pickleCommentMapper: PickleCommentMapper
) : SavePickleCommentPort, ReadPickleCommentPort, ReadPickleCommentsPort, DeletePickleCommentPort {
    override fun readById(pickleCommentId: Long): PickleComment? =
        pickleCommentRepository.findById(pickleCommentId)?.let { pickleCommentMapper.toDomain(it) }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(pickleComment: PickleComment): PickleComment =
        pickleCommentMapper.toDomain(pickleCommentRepository.save(pickleCommentMapper.toEntity(pickleComment)))

    override fun readAllPagedFromViedoIdAndIndexAndSize(videoId: String, page: Int, size: Int): List<PickleComment> =
        pickleCommentRepository.findAllByVideoId(videoId, PageRequest.of(page, size) as Pageable).content
            .map { pickleCommentMapper.toDomain(it) }.toList()

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(pickleComment: PickleComment) {
        pickleCommentRepository.delete(pickleCommentMapper.toEntity(pickleComment))
    }
}
