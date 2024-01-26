package com.info.maeumgagym.pickle.exception

import com.info.maeumgagym.common.exception.ErrorCode
import com.info.maeumgagym.common.exception.MaeumGaGymException

object PickleMismatchedException : MaeumGaGymException(ErrorCode.PICKLE_MISMATCHED)
