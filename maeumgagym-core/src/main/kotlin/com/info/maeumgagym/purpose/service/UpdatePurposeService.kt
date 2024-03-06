package com.info.maeumgagym.purpose.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.purpose.dto.request.UpdatePurposeRequest
import com.info.maeumgagym.purpose.model.Purpose
import com.info.maeumgagym.purpose.port.`in`.UpdatePurposeUseCase
import com.info.maeumgagym.purpose.port.out.ReadPurposePort
import com.info.maeumgagym.purpose.port.out.SavePurposePort

@UseCase
internal class UpdatePurposeService(
    private val savePurposePort: SavePurposePort,
    private val readPurposePort: ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : UpdatePurposeUseCase {

    override fun updatePurposeFromId(id: Long, req: UpdatePurposeRequest) {
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
                Purpose(
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
