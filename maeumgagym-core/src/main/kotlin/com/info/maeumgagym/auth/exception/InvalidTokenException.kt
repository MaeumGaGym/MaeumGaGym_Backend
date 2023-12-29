package com.info.maeumgagym.auth.exception

import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.common.exception.ErrorCode

object InvalidTokenException : MaeumGaGymException(ErrorCode.INVALID_TOKEN)
