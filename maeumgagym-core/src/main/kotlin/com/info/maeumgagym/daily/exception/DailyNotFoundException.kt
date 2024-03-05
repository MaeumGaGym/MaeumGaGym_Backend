package com.info.maeumgagym.daily.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object DailyNotFoundException : MaeumGaGymException(ErrorCode.DAILY_NOT_FOUND)
