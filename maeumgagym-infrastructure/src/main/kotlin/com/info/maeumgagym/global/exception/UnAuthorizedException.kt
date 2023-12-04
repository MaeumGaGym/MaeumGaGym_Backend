package com.info.maeumgagym.global.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object UnAuthorizedException : MaeumGaGymException(ErrorCode.UNAUTHORIZED)
