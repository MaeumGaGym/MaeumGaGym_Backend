package com.info.maeumgagym.user.port.`in`

import com.info.maeumgagym.user.dto.request.UpdateUserInfoRequest

interface UpdateUserInfoUseCase {
    fun update(req: UpdateUserInfoRequest)
}
