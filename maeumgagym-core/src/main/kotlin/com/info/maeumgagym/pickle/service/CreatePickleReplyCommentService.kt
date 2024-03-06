package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.pickle.dto.request.PickleCommentRequest
import com.info.maeumgagym.pickle.model.PickleReply
import com.info.maeumgagym.pickle.port.`in`.CreatePickleReplyCommentUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPicklePort
import com.info.maeumgagym.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.pickle.port.out.SavePickleReplyPort

@UseCase
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
            ?: throw BusinessLogicException.PICKLE_COMMENT_NOT_FOUND

        if (!existsPicklePort.existsById(videoId)) {
            throw BusinessLogicException.PICKLE_NOT_FOUND
        }

        if (parentComment.videoId != videoId) {
            throw BusinessLogicException(400, "Pickle Mismatched with Parent Comment")
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
