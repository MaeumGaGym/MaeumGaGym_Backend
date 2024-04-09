package com.info.maeumgagym.pickle.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.pickle.dto.request.CreatePickleRequest
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.port.`in`.CreatePickleUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPicklePort
import com.info.maeumgagym.pickle.port.out.ReadVideoIdAndUploaderIdPort
import com.info.maeumgagym.pickle.port.out.SavePicklePort

@UseCase
internal class CreatePickleService(
    private val savePicklePort: SavePicklePort,
    private val existsPicklePort: ExistsPicklePort,
    private val readVideoIdAndUploaderIdPort: ReadVideoIdAndUploaderIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreatePickleUseCase {

    override fun createPickle(req: CreatePickleRequest): LocationSubjectDto {
        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // 이미 이 id의 영상이 존재한다면 -> 예외 발생
        if (existsPicklePort.existsById(req.videoId)) throw BusinessLogicException.ALREADY_EXIST_PICKLE

        // 태그 중에서
        req.tags.forEach {
            // 10자보다 큰 태그가 있다면 -> 예외 발생
            if (it.length > 10) throw BusinessLogicException.TAG_TOO_LONG
        }

        // 만약 영상 서버에 업로드된 영상이 아니라면 -> 예외 발생
        val videoIdAndUploaderId = readVideoIdAndUploaderIdPort.readByVideoId(req.videoId)
            ?: throw BusinessLogicException(400, "Not Uploaded Pickle In Video Server.")

        // 만약 영상 서버에 업로드한 사람과 서버에 등록하려는 사람이 다르다면 -> 예외 발생
        if (videoIdAndUploaderId.uploaderId != user.id!!) {
            throw SecurityException(403, "Video's uploader and Current User Mismatched.")
        }

        // 피클 저장
        val pickle = savePicklePort.save(
            Pickle(
                videoId = req.videoId,
                title = req.title,
                description = req.description,
                uploader = user,
                tags = req.tags
            )
        )

        return LocationSubjectDto(pickle.videoId)
    }
}
