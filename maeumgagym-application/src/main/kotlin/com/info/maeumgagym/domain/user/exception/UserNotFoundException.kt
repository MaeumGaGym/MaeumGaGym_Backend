package com.info.maeumgagym.domain.user.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object UserNotFoundException : MaeumGaGymException(
    ErrorCode.USER_NOT_FOUND
)
