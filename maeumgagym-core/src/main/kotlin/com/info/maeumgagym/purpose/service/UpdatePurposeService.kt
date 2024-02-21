package com.info.maeumgagym.purpose.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.purpose.dto.request.CreatePurposeRequest
import com.info.maeumgagym.purpose.dto.request.UpdatePurposeRequest
import com.info.maeumgagym.purpose.exception.PurposeNotFoundException
import com.info.maeumgagym.purpose.exception.StartDateCannotAfterThanEndDateException
import com.info.maeumgagym.purpose.model.Purpose
import com.info.maeumgagym.purpose.port.`in`.CreatePurposeUseCase
import com.info.maeumgagym.purpose.port.`in`.UpdatePurposeUseCase
import com.info.maeumgagym.purpose.port.out.ReadPurposePort
import com.info.maeumgagym.purpose.port.out.SavePurposePort

@UseCase
internal class UpdatePurposeService(
    private val savePurposePort: SavePurposePort,
    private val readPurposePort: ReadPurposePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : UpdatePurposeUseCase {

    override fun updatePurpose(id: Long, req: UpdatePurposeRequest) {
        val user = readCurrentUserPort.readCurrentUser()

        val purpose = readPurposePort.readById(id) ?: throw PurposeNotFoundException

        if (purpose.user.id!! != user.id!!) {
            throw PermissionDeniedException
        }

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
