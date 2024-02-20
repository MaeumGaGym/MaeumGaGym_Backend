package com.info.maeumgagym.purpose.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.purpose.dto.request.CreatePurposeRequest
import com.info.maeumgagym.purpose.exception.StartDateCannotAfterThanEndDateException
import com.info.maeumgagym.purpose.model.Purpose
import com.info.maeumgagym.purpose.port.`in`.CreatePurposeUseCase
import com.info.maeumgagym.purpose.port.out.SavePurposePort

@UseCase
internal class CreatePurposeService(
    private val savePurposePort: SavePurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreatePurposeUseCase {

    override fun createPurpose(req: CreatePurposeRequest) {
        val user = readCurrentUserPort.readCurrentUser()

        if (req.startDate.isAfter(req.endDate)) {
            throw StartDateCannotAfterThanEndDateException
        }

        req.run {
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
    }
}
