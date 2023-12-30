package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.AlreadyWithdrawalUserException
import com.info.maeumgagym.auth.exception.UnAuthorizedException
import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.user.model.DeleteAt
import com.info.maeumgagym.user.port.out.DeleteUserPort
import com.info.maeumgagym.user.port.out.ExistsDeletedUserByIdPort
import com.info.maeumgagym.user.port.out.SaveDeletedAtPort

@UseCase
class WithdrawalUserService(
    private val deleteUserPort: DeleteUserPort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val existsDeletedUserByIdPort: ExistsDeletedUserByIdPort,
    private val saveDeleteAtPort: SaveDeletedAtPort
) : WithdrawalUserUseCase {
    override fun withdrawal() {
        val user = try {
            readCurrentUserPort.readCurrentUser()
        } catch (e: UnAuthorizedException) {
            throw AlreadyWithdrawalUserException
        }

        if (existsDeletedUserByIdPort.existsByIdInNative(user.oauthId)) throw AlreadyWithdrawalUserException

        saveDeleteAtPort.save(DeleteAt(user.id))

        deleteUserPort.deleteUser(user)
    }
}
