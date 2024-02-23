package com.info.maeumgagym.feign.exception

import com.info.maeumgagym.common.exception.MaeumGaGymException

object FeignBadRequestException : MaeumGaGymException(MaeumGaGymException.FEIGN_BAD_REQUEST) {

    private fun readResolve(): Any = FeignBadRequestException
}
