package com.info.maeumgagym.global

internal abstract class MaeumGaGymTestException(
    errorCode: TestErrorCode
) : RuntimeException(errorCode.message)
