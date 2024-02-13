package com.info.maeumgagym.goal.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object StartDateIsAfterEndDateException : MaeumGaGymException(ErrorCode.START_DATE_IS_AFTER_END_TIME)
