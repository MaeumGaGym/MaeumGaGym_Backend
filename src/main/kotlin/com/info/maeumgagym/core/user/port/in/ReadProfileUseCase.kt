package com.info.maeumgagym.core.user.port.`in`

import com.info.maeumgagym.core.user.dto.response.UserProfileResponse

interface ReadProfileUseCase {

    fun readProfile(): UserProfileResponse
}
