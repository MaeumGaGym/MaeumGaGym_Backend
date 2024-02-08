package com.info.maeumgagym.step.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object AlreadyExistStepException : MaeumGaGymException(ErrorCode.ALREADY_EXIST_STEP)
