package com.info.maeumgagym.core.user.port.`in`

import com.info.maeumgagym.core.user.dto.request.UpdateUserInfoRequest

interface UpdateUserInfoUseCase {
    fun update(req: UpdateUserInfoRequest)
}
