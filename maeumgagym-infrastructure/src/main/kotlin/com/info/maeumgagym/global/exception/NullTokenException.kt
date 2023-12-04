package com.info.maeumgagym.global.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object NullTokenException : MaeumGaGymException(
    ErrorCode.NULL_TOKEN
)
