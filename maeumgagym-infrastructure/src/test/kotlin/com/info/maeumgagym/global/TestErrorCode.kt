package com.info.maeumgagym.global

import com.info.maeumgagym.common.exception.ErrorCode

enum class TestErrorCode(
    val message: String
) {
    // Wakatime
    WAKATIME_DOES_NOT_SAVED("와카타임이 저장되지 않았습니다."),
    ERROR_TOO_BIG("발생한 오차가 허용된 오차보다 큽니다."),

    // Quote
    MISMATCH_QUOTE_AND_QUOTER("명언과 화자가 어울리지 않습니다.")
}
