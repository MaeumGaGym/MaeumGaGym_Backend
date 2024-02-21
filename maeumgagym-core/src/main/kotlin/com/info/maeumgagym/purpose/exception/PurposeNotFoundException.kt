package com.info.maeumgagym.purpose.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object PurposeNotFoundException : MaeumGaGymException(ErrorCode.PURPOSE_NOT_FOUND)
