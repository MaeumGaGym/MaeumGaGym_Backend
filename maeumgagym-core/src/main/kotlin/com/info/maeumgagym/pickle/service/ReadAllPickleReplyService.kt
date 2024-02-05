package com.info.maeumgagym.pickle.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.pickle.dto.response.PickleReplyListResponse
import com.info.maeumgagym.pickle.exception.PickleCommentNotFoundException
import com.info.maeumgagym.pickle.port.`in`.LoadAllPickleReplyUseCase
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.pickle.port.out.ReadPickleReplyPort

@ReadOnlyUseCase
internal class ReadAllPickleReplyService(
    private val readPickleReplyPort: ReadPickleReplyPort,
    private val readPickleCommentPort: ReadPickleCommentPort
) : LoadAllPickleReplyUseCase {
    override fun loadAllPickleReply(parentCommentId: Long, page: Int, size: Int): PickleReplyListResponse {
        val replies = readPickleReplyPort.readAllByParentComment(
            readPickleCommentPort.readById(parentCommentId) ?: throw PickleCommentNotFoundException,
            page,
            size
        )
        return PickleReplyListResponse(replies.map { it.toResponse(it) }.toList())
    }
}
