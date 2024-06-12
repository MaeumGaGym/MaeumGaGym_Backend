package com.info.maeumgagym.core.user.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.auth.service.DuplicatedCheckService
import com.info.maeumgagym.core.user.dto.request.UpdateUserInfoRequest
import com.info.maeumgagym.core.user.model.PhysicalInfoModel
import com.info.maeumgagym.core.user.port.`in`.UpdateUserInfoUseCase
import com.info.maeumgagym.core.user.port.out.SaveUserPort

@UseCase
internal class UpdateUserInfoService(
    private val saveUserPort: SaveUserPort,
    private val duplicatedCheckService: DuplicatedCheckService,
    private val readCurrentUserPort: ReadCurrentUserPort
) : UpdateUserInfoUseCase {

    override fun update(req: UpdateUserInfoRequest) {
        if (duplicatedCheckService.existByNickname(req.nickname)) {
            throw BusinessLogicException.ALREADY_EXIST_USER
        }
        val user = readCurrentUserPort.readCurrentUser()

        saveUserPort.save(
            req.run {
                user.copy(
                    nickname = nickname,
                    physicalInfoModel = PhysicalInfoModel(
                        weight = weight,
                        height = height,
                        genderModel = gender
                    )
                )
            }
        )
    }
}
