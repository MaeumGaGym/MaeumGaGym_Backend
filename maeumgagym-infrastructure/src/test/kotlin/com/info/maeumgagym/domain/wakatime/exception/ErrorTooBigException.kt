package com.info.maeumgagym.domain.wakatime.exception

import com.info.maeumgagym.error.MaeumGaGymTestException
import com.info.maeumgagym.error.TestErrorCode

internal object ErrorTooBigException : MaeumGaGymTestException(TestErrorCode.ERROR_TOO_BIG)
