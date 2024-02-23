package com.info.maeumgagym.feign.exception

import com.info.maeumgagym.common.exception.MaeumGaGymException

object FeignForbbidenException : MaeumGaGymException(MaeumGaGymException.FEIGN_FORBIDDEN) {
    private fun readResolve(): Any = FeignForbbidenException
}
