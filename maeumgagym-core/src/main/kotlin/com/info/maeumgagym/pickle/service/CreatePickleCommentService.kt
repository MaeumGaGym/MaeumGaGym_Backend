package com.info.maeumgagym.pickle.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.pickle.dto.request.PickleCommentRequest
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.port.`in`.CreatePickleCommentUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPicklePort
import com.info.maeumgagym.pickle.port.out.SavePickleCommentPort

@UseCase
internal class CreatePickleCommentService(
    private val savePickleCommentPort: SavePickleCommentPort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val existsPicklePort: ExistsPicklePort
) : CreatePickleCommentUseCase {

    override fun createPickleComment(pickleCommentRequest: PickleCommentRequest, videoId: String): LocationSubjectDto {
        val user = readCurrentUserPort.readCurrentUser()

        if (!existsPicklePort.existsById(videoId)) {
            throw BusinessLogicException.ALREADY_EXIST_PICKLE
        }

        val pickleComment = pickleCommentRequest.run {
            savePickleCommentPort.save(
                PickleComment(
                    content = content,
                    videoId = videoId,
                    writer = user
                )
            )
        }

        return LocationSubjectDto(pickleComment.id!!)
    }
}
