package com.info.maeumgagym.routine.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object RoutineNotFoundException : MaeumGaGymException(
    ErrorCode.ROUTINE_NOT_FOUND
)
