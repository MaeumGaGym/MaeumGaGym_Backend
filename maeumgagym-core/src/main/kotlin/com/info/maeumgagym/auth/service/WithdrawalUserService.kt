package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.`in`.WithdrawalUserUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.user.port.out.DeleteUserPort
import org.springframework.transaction.annotation.Transactional

@Transactional
@UseCase
class WithdrawalUserService(
    private val deleteUserPort: DeleteUserPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : WithdrawalUserUseCase {
    override fun withdrawal() {
        val user = readCurrentUserPort.readCurrentUser()
        deleteUserPort.deleteUser(user)
    }
}
