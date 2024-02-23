package com.info.maeumgagym.feign.exception

import com.info.maeumgagym.common.exception.MaeumGaGymException

object FeignUnAuthorizationException : MaeumGaGymException(MaeumGaGymException.FEIGN_UNAUTHORIZED) {
    private fun readResolve(): Any = FeignUnAuthorizationException
}
