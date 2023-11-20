package com.info.maeumgagym.feign.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object FeignForbbidenException: MaeumGaGymException(ErrorCode.FEIGN_FORBIDDEN) {
    private fun readResolve(): Any = FeignForbbidenException
}