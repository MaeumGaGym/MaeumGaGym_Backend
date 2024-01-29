package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.model.PickleLike
import com.info.maeumgagym.pickle.port.`in`.LikePickleUseCase
import com.info.maeumgagym.pickle.port.out.*
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
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
        val pickle = readPicklePort.readById(id) ?: throw PickleNotFoundException

        // 좋아요 객체 nullable로 불러오기
        val like = readPickleLikePort.readByPickleAndUser(pickle, user)

        // 좋아요를 눌렀다면
        if (like != null) {
            // 좋아요 삭제하기
            deletePickleLikePort.delete(like)
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
        } else { // 좋아요를 안눌렀다면
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
