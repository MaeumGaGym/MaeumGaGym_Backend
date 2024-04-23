package com.info.maeumgagym.core.user.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.auth.service.DuplicatedCheckService
import com.info.maeumgagym.core.common.exception.BusinessLogicException

@UseCase
internal class UpdateUserInfoService(
    private val saveUserPort: com.info.maeumgagym.core.user.port.out.SaveUserPort,
    private val duplicatedCheckService: DuplicatedCheckService,
    private val readCurrentUserPort: ReadCurrentUserPort
) : com.info.maeumgagym.core.user.port.`in`.UpdateUserInfoUseCase {

    override fun update(req: com.info.maeumgagym.core.user.dto.request.UpdateUserInfoRequest) {
        if (duplicatedCheckService.existByNickname(req.nickname)) {
            throw BusinessLogicException.ALREADY_EXIST_USER
        }
        val user = readCurrentUserPort.readCurrentUser()

        saveUserPort.save(
            req.run {
                user.copy(
                    nickname = nickname,
                    physicalInfoModel = com.info.maeumgagym.core.user.model.PhysicalInfoModel(
                        weight = weight,
                        height = height,
                        genderModel = genderModel
                    )
                )
            }
        )
    }
}
