package com.info.maeumgagym.core.purpose.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.purpose.dto.request.CreatePurposeRequest
import com.info.maeumgagym.core.purpose.model.Purpose
import com.info.maeumgagym.core.purpose.port.`in`.CreatePurposeUseCase
import com.info.maeumgagym.core.purpose.port.out.SavePurposePort

@UseCase
internal class CreatePurposeService(
    private val savePurposePort: SavePurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreatePurposeUseCase {

    override fun createPurpose(req: CreatePurposeRequest): LocationSubjectDto {
        val user = readCurrentUserPort.readCurrentUser()

        if (req.startDate.isAfter(req.endDate)) {
            throw BusinessLogicException.START_DATE_MUST_BE_BEFORE_THAN_END_DATE
        }

        val purpose = req.run {
            savePurposePort.save(
                Purpose(
                    title = title,
                    content = content,
                    startDate = startDate,
                    endDate = endDate,
                    user = user
                )
            )
        }

        return LocationSubjectDto(purpose.id!!)
    }
}
