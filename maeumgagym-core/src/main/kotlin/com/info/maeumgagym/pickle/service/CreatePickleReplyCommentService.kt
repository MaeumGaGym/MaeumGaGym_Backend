package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.dto.request.PickleCommentRequest
import com.info.maeumgagym.pickle.exception.PickleCommentNotFoundException
import com.info.maeumgagym.pickle.exception.PickleMisMatchException
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.model.PickleReply
import com.info.maeumgagym.pickle.port.`in`.CreatePickleReplyCommentUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPickleByIdPort
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.pickle.port.out.SavePickleReplyCommentPort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class CreatePickleReplyCommentService(
    private val savePickleReplyCommentPort: SavePickleReplyCommentPort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPickleCommentPort: ReadPickleCommentPort,
    private val existsPickleByIdPort: ExistsPickleByIdPort
) : CreatePickleReplyCommentUseCase {
    override fun createPickleReplyComment(
        pickleCommentRequest: PickleCommentRequest,
        videoId: String,
        parentId: Long
    ) {
        val user = readCurrentUserPort.readCurrentUser()
        val parentComment = readPickleCommentPort.readPickleCommentById(parentId)
            ?: throw PickleCommentNotFoundException

        if (!existsPickleByIdPort.existsPickleById(videoId)) {
            throw PickleNotFoundException
        }

        if (parentComment.videoId != videoId) {
            throw PickleMisMatchException
        }

        pickleCommentRequest.run {
            savePickleReplyCommentPort.savePickleReplyComment(
                PickleReply(
                    content = content,
                    videoId = videoId,
                    writer = user,
                    parentComment = parentComment
                )
            )
        }
    }
}
