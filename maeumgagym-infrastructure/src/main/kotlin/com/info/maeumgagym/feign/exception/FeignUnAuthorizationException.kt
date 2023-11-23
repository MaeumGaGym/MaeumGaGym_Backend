package com.info.maeumgagym.feign.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object FeignUnAuthorizationException : MaeumGaGymException(ErrorCode.FEIGN_UNAUTHORIZED) {
    private fun readResolve(): Any = FeignUnAuthorizationException
}
