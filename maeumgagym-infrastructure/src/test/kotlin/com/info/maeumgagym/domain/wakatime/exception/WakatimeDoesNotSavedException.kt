package com.info.maeumgagym.domain.wakatime.exception

import com.info.maeumgagym.error.MaeumGaGymTestException
import com.info.maeumgagym.error.TestErrorCode

internal object WakatimeDoesNotSavedException : MaeumGaGymTestException(TestErrorCode.WAKATIME_DOES_NOT_SAVED)
