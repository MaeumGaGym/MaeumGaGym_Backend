package com.info.maeumgagym.feign.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object FeignBadRequestException : MaeumGaGymException(ErrorCode.FEIGN_BAD_REQUEST) {

    private fun readResolve(): Any = FeignBadRequestException
}
