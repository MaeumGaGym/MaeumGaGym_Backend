package com.info.maeumgagym.core.pickle.service

import com.info.maeumgagym.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.pickle.dto.request.PickleCommentRequest
import com.info.maeumgagym.core.pickle.model.PickleReply
import com.info.maeumgagym.core.pickle.port.`in`.CreatePickleReplyCommentUseCase
import com.info.maeumgagym.core.pickle.port.out.ExistsPicklePort
import com.info.maeumgagym.core.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.core.pickle.port.out.SavePickleReplyPort

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
    ): LocationSubjectDto {
        val user = readCurrentUserPort.readCurrentUser()
        val parentComment = readPickleCommentPort.readById(parentId)
            ?: throw BusinessLogicException.PICKLE_COMMENT_NOT_FOUND

        if (!existsPicklePort.existsById(videoId)) {
            throw BusinessLogicException.PICKLE_NOT_FOUND
        }

        if (parentComment.videoId != videoId) {
            throw BusinessLogicException(400, "Pickle Mismatched with Parent Comment")
        }

        val pickleComment = pickleCommentRequest.run {
            savePickleReplyPort.save(
                PickleReply(
                    content = content,
                    videoId = videoId,
                    writer = user,
                    parentComment = parentComment
                )
            )
        }

        return LocationSubjectDto(pickleComment.id!!)
    }
}
