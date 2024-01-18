package com.info.maeumgagym.domain.wakatime.exception

import com.info.maeumgagym.global.MaeumGaGymTestException
import com.info.maeumgagym.global.TestErrorCode

internal object WakatimeDoesNotSavedException : MaeumGaGymTestException(TestErrorCode.WAKATIME_DOES_NOT_SAVED) {
}
