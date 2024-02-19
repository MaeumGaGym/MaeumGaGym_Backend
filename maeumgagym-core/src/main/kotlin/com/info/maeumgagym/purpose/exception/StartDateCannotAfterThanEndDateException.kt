package com.info.maeumgagym.purpose.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object StartDateCannotAfterThanEndDateException : MaeumGaGymException(ErrorCode.START_DATE_CANNOT_AFTER_THAN_END_TIME)
