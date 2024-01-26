package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.model.PickleLike
import com.info.maeumgagym.pickle.port.`in`.LikePickleUseCase
import com.info.maeumgagym.pickle.port.out.DeletePickleLikePort
import com.info.maeumgagym.pickle.port.out.ReadPickleLikePort
import com.info.maeumgagym.pickle.port.out.SavePickleLikePort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class LikePickleService(
    private val savePickleLikePort: SavePickleLikePort,
    private val readPickleLikePort: ReadPickleLikePort,
    private val deletePickleLikePort: DeletePickleLikePort
    private val readCurrentUserPort: ReadCurrentUserPort
) : LikePickleUseCase {

    override fun likePickle(id: String) {
        // 현재 로그인한 유저 추출
        val user = readCurrentUserPort.readCurrentUser()

        // 좋아요 객체 nullable로 불러오기
        val like = readPickleLikePort.readByVideoIdAndUser(id, user)

        // 좋아요를 눌렀다면
        if (like != null) {
            // 좋아요 삭제하기
            deletePickleLikePort.delete(like)
        } else { // 좋아요를 안눌렀다면
            // 좋아요 추가하기
            savePickleLikePort.save(
                PickleLike(id, user)
            )
        }
    }
}
