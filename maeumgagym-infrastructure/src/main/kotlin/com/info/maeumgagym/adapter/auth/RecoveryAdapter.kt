package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ReadUserPort
import com.info.maeumgagym.user.port.out.RecoveryUserPort
import com.info.maeumgagym.user.port.out.SaveUserPort
import org.springframework.stereotype.Component

@Component
internal class RecoveryAdapter(
    private val readUserPort: ReadUserPort,
    private val saveUserPort: SaveUserPort
) : RecoveryUserPort {

    override fun recovery(oauthId: String) {
        // 해딩 유저를 삭제된 유저들 중에서 확인
        val deletedUser = readUserPort.readDeletedByOauthId(oauthId) ?: throw BusinessLogicException.USER_NOT_FOUND

        // 유저의 소프트 딜리트 변경
        deletedUser.apply {
            saveUserPort.save(
                User(
                    id = id,
                    nickname = nickname,
                    roles = roles,
                    oauthId = oauthId,
                    profileImage = profileImage,
                    isDeletedAt = null,
                    physicalInfoModel = physicalInfoModel
                )
            )
        }
    }
}
