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

        val deletedUser = findDeletedUserByIdPort.findByIdOrNullInNative(oauthId) ?: throw UserNotFoundException

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

        deleteDeletedAtPort.delete(deletedUser.id)
    }
}
