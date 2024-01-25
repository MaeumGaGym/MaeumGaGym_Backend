package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.dto.request.CreatePickleRequest
import com.info.maeumgagym.pickle.exception.AlreadyExistPickleException
import com.info.maeumgagym.pickle.exception.NotUploadedToVideoServerException
import com.info.maeumgagym.pickle.exception.VideoAndUploaderMismatchException
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.port.`in`.CreatePickleUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPicklePort
import com.info.maeumgagym.pickle.port.out.ReadVideoIdAndUploaderIdPort
import com.info.maeumgagym.pickle.port.out.SavePicklePort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class CreatePickleService(
    private val savePicklePort: SavePicklePort,
    private val existsPicklePort: ExistsPicklePort,
    private val readVideoIdAndUploaderIdPort: ReadVideoIdAndUploaderIdPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreatePickleUseCase {

    override fun createPickle(req: CreatePickleRequest) {
        // 토큰으로 유저 찾기
        val user = readCurrentUserPort.readCurrentUser()

        // 이미 이 id의 영상이 존재한다면 -> 예외 발생
        if (existsPicklePort.existsById(req.videoId)) throw AlreadyExistPickleException

        // 만약 영상 서버에 업로드된 영상이 아니라면 -> 예외 발생
        val videoIdAndUploaderId = readVideoIdAndUploaderIdPort.readByVideoId(req.videoId)
            ?: throw NotUploadedToVideoServerException

        // 만약 영상 서버에 업로드한 사람과 서버에 등록하려는 사람이 다르다면 -> 예외 발생
        if (videoIdAndUploaderId.uploaderId != user.id!!) throw VideoAndUploaderMismatchException

        // 피클 저장
        savePicklePort.save(
            Pickle(
                videoId = req.videoId,
                title = req.title,
                description = req.description,
                uploader = user,
                tags = req.tags
            )
        )
    }
}
