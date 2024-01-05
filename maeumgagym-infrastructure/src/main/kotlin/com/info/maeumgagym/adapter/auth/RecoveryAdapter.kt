package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.port.out.DeleteDeletedAtPort
import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.FindDeletedUserByIdPort
import com.info.maeumgagym.user.port.out.RecoveryUserPort
import com.info.maeumgagym.user.port.out.SaveUserPort
import org.springframework.stereotype.Component

@Component
class RecoveryAdapter(
    private val findDeletedUserByIdPort: FindDeletedUserByIdPort,
    private val deleteDeletedAtPort: DeleteDeletedAtPort,
    private val saveUserPort: SaveUserPort
): RecoveryUserPort {

    override fun recovery(oauthId: String) {
        // 해딩 유저를 삭제된 유저들 중에서 확인
        val deletedUser = findDeletedUserByIdPort.findByIdOrNullInNative(oauthId) ?: throw UserNotFoundException

        // 유저의 소프트 딜리트 변경
        deletedUser.apply {
            saveUserPort.saveUser(
                User(
                    id = id,
                    nickname = nickname,
                    roles = roles,
                    oauthId = oauthId,
                    profileImage = profileImage,
                    isDeleted = false
                )
            )
        }

        // 삭제된 유저 생명 주기 테이블 삭제
        deleteDeletedAtPort.delete(deletedUser.id)
    }
}
