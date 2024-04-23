package com.info.maeumgagym.core.purpose.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.common.exception.SecurityException

@UseCase
internal class UpdatePurposeService(
    private val savePurposePort: com.info.maeumgagym.core.purpose.port.out.SavePurposePort,
    private val readPurposePort: com.info.maeumgagym.core.purpose.port.out.ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : com.info.maeumgagym.core.purpose.port.`in`.UpdatePurposeUseCase {

    override fun updatePurposeFromId(id: Long, req: com.info.maeumgagym.core.purpose.dto.request.UpdatePurposeRequest) {
        val user = readCurrentUserPort.readCurrentUser()

        val purpose = readPurposePort.readById(id) ?: throw BusinessLogicException.PURPOSE_NOT_FOUND

        if (purpose.user.id!! != user.id!!) {
            throw SecurityException.PERMISSION_DENIED
        }

        if (req.startDate.isAfter(req.endDate)) {
            throw BusinessLogicException.START_DATE_MUST_BE_BEFORE_THAN_END_DATE
        }

        req.run {
            savePurposePort.save(
                com.info.maeumgagym.core.purpose.model.Purpose(
                    title = title,
                    content = content,
                    startDate = startDate,
                    endDate = endDate,
                    user = purpose.user,
                    id = purpose.id
                )
            )
        }
    }
}
