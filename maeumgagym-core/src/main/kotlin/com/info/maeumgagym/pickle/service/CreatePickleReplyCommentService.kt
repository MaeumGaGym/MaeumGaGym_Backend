package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.dto.request.PickleCommentRequest
import com.info.maeumgagym.pickle.exception.PickleCommentNotFoundException
import com.info.maeumgagym.pickle.exception.PickleMismatchException
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.model.PickleReply
import com.info.maeumgagym.pickle.port.`in`.CreatePickleReplyCommentUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPicklePort
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.pickle.port.out.SavePickleReplyPort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class CreatePickleReplyCommentService(
    private val savePickleReplyPort: SavePickleReplyPort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPickleCommentPort: ReadPickleCommentPort,
    private val existsPicklePort: ExistsPicklePort
) : CreatePickleReplyCommentUseCase {
    override fun createPickleReplyComment(
        pickleCommentRequest: PickleCommentRequest,
        videoId: String,
        parentId: Long
    ) {
        val user = readCurrentUserPort.readCurrentUser()
        val parentComment = readPickleCommentPort.readById(parentId)
            ?: throw PickleCommentNotFoundException

        if (!existsPicklePort.existsById(videoId)) {
            throw PickleNotFoundException
        }

        if (parentComment.videoId != videoId) {
            throw PickleMismatchException
        }

        pickleCommentRequest.run {
            savePickleReplyPort.save(
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
