package com.info.maeumgagym.core.pickle.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.pickle.model.Pickle
import com.info.maeumgagym.core.pickle.model.PickleLike
import com.info.maeumgagym.core.pickle.port.`in`.LikePickleUseCase
import com.info.maeumgagym.core.pickle.port.out.*

@UseCase
internal class LikePickleService(
    private val savePickleLikePort: SavePickleLikePort,
    private val readPickleLikePort: ReadPickleLikePort,
    private val deletePickleLikePort: DeletePickleLikePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val savePicklePort: SavePicklePort,
    private val readPicklePort: ReadPicklePort
) : LikePickleUseCase {

    override fun likePickle(id: String) {
        // 현재 로그인한 유저 추출
        val user = readCurrentUserPort.readCurrentUser()

        // 피클 불러오기 : null이라면 -> 예외
        val pickle = readPicklePort.readById(id) ?: throw BusinessLogicException.PICKLE_NOT_FOUND

        // 좋아요 객체 nullable로 불러오기
        readPickleLikePort.readByPickleAndUser(pickle, user)?.apply { // 좋아요를 눌렀다면
            // 좋아요 삭제하기
            deletePickleLikePort.delete(this)
            // 좋아요 수 - 1 저장
            savePicklePort.save(
                pickle.run {
                    Pickle(
                        videoId = videoId,
                        title = title,
                        description = description,
                        uploader = uploader,
                        likes = likes,
                        likeCount = likeCount - 1,
                        commentCount = commentCount,
                        tags = tags,
                        createdAt = createdAt
                    )
                }
            )
        } ?: apply { // 좋아요를 안눌렀다면
            // 좋아요 추가하기
            savePickleLikePort.save(
                PickleLike(pickle, user)
            )
            // 좋아요 수 + 1 저장
            savePicklePort.save(
                pickle.run {
                    Pickle(
                        videoId = videoId,
                        title = title,
                        description = description,
                        uploader = uploader,
                        likes = likes,
                        likeCount = likeCount + 1,
                        commentCount = commentCount,
                        tags = tags,
                        createdAt = createdAt
                    )
                }
            )
        }
    }
}
