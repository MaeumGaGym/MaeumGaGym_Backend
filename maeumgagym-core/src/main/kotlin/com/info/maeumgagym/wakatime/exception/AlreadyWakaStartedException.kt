package com.info.maeumgagym.wakatime.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object AlreadyWakaStartedException : MaeumGaGymException(ErrorCode.ALREADY_STARTED_WAKA)
