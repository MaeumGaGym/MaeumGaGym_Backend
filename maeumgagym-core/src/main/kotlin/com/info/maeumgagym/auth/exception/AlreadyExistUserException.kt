package com.info.maeumgagym.auth.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object AlreadyExistUserException : MaeumGaGymException(ErrorCode.ALREADY_EXIST_USER)
