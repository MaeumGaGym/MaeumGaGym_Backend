package com.info.maeumgagym.global.exception

import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.ErrorCode

object ExpiredTokenException : MaeumGaGymException(
    ErrorCode.EXPIRED_TOKEN
)
