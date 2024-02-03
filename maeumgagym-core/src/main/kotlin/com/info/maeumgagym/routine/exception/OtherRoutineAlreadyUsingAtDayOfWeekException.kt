package com.info.maeumgagym.routine.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object OtherRoutineAlreadyUsingAtDayOfWeekException :
    MaeumGaGymException(ErrorCode.OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK)
