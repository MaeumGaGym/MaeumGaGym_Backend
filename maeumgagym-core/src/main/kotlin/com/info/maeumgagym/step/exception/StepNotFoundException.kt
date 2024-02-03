package com.info.maeumgagym.step.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object StepNotFoundException : MaeumGaGymException(ErrorCode.STEP_NOT_FOUND)
