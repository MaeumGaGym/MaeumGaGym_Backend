package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.dto.request.UpdatePickleRequest
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.exception.TagTooLongException
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.port.`in`.UpdatePickleUseCase
import com.info.maeumgagym.pickle.port.out.ReadPicklePort
import com.info.maeumgagym.pickle.port.out.SavePicklePort

@UseCase
internal class UpdatePickleService(
    private val savePicklePort: SavePicklePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPicklePort: ReadPicklePort
) : UpdatePickleUseCase {

    override fun updatePickle(id: String, req: UpdatePickleRequest) {
        // WHAT : token으로 user 불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // WHEN : 태그 중에서
        req.tags.forEach {
            // WHEN : 10자보다 큰 태그가 있다면 -> Exception 처리
            if (it.length > 10) throw TagTooLongException
        }

        // WHEN : id로 pickle을 찾을 수 없는 경우 -> Exception 처리
        val pickle = readPicklePort.readById(id) ?: throw PickleNotFoundException

        // WHEN : 영상을 올린 사람의 요청이 아닌 경우 -> Exception 처리
        if (pickle.uploader != user) throw PermissionDeniedException

        pickle.run {
            savePicklePort.save( // WHAT : 객체 변경 후 반환
                Pickle(
                    videoId = this.videoId,
                    title = req.title,
                    description = req.description,
                    uploader = uploader,
                    tags = req.tags,
                    likeCount = likeCount,
                    createdAt = createdAt,
                    isDeleted = isDeleted
                )
            )
        }
    }
}
