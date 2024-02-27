package com.info.maeumgagym.purpose.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.purpose.exception.PurposeNotFoundException
import com.info.maeumgagym.purpose.model.Purpose
import com.info.maeumgagym.purpose.port.`in`.DeletePurposeUseCase
import com.info.maeumgagym.purpose.port.out.DeletePurposePort
import com.info.maeumgagym.purpose.port.out.ReadPurposePort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class DeletePurposeService(
    private val readPurposePort: ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val deletePurposePort: DeletePurposePort
) : DeletePurposeUseCase {

    override fun deletePurposeFromId(id: Long) {
        val user = readCurrentUserPort.readCurrentUser()

        val purpose: Purpose = readPurposePort.readById(id)
            ?.takeIf { it.user == user } ?: throw PurposeNotFoundException

        deletePurposePort.deleteById(purpose.id!!)
    }
}
