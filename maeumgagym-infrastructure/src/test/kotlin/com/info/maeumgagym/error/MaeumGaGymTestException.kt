package com.info.maeumgagym.error

internal abstract class MaeumGaGymTestException(
    errorCode: TestErrorCode
) : RuntimeException(errorCode.message)
