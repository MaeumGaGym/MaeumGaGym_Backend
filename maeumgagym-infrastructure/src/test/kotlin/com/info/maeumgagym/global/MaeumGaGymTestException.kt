package com.info.maeumgagym.global

abstract class MaeumGaGymTestException(
    errorCode: TestErrorCode
) : RuntimeException(errorCode.message)
