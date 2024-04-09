package com.info.maeumgagym.domain.pickle

import com.info.common.responsibility.PersistenceAdapter
import com.info.maeumgagym.domain.pickle.mapper.PickleCommentMapper
import com.info.maeumgagym.domain.pickle.repository.PickleReplyRepository
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.model.PickleReply
import com.info.maeumgagym.pickle.port.out.DeletePickleReplyPort
import com.info.maeumgagym.pickle.port.out.ReadPickleReplyPort
import com.info.maeumgagym.pickle.port.out.SavePickleReplyPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@PersistenceAdapter
internal class PickleReplyPersistenceAdapter(
    private val pickleReplyRepository: PickleReplyRepository,
    private val pickleCommentMapper: PickleCommentMapper
) : SavePickleReplyPort, ReadPickleReplyPort, DeletePickleReplyPort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(pickleReply: PickleReply): PickleReply =
        pickleCommentMapper.toDomain(
            pickleReplyRepository.save(pickleCommentMapper.toEntity(pickleReply))
        )

    override fun readById(id: Long): PickleReply? =
        pickleReplyRepository.findById(id)?.let {
            pickleCommentMapper.toDomain(it)
        }

    override fun readAllByParentComment(
        parentComment: PickleComment,
        page: Int,
        size: Int
    ): List<PickleReply> =
        pickleReplyRepository.findAllByParentComment(
            pickleCommentMapper.toEntity(parentComment),
            PageRequest.of(page, size) as Pageable
        ).content.map { pickleCommentMapper.toDomain(it) }.toList()

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(pickleReply: PickleReply) =
        pickleReplyRepository.delete(pickleCommentMapper.toEntity(pickleReply))
}
