package com.info.maeumgagym.feign.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object FeignDefaultException: MaeumGaGymException(ErrorCode.FEIGN_SERVER_ERROR) {
    private fun readResolve(): Any = FeignDefaultException
}