package com.info.maeumgagym.global.exception

import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.ErrorCode

object InternalServerErrorException : MaeumGaGymException(
    ErrorCode.INTERNAL_SERVER_ERROR
)
