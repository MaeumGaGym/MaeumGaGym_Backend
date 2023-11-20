package com.info.maeumgagym.common.exception

abstract class MaeumGaGymException(
    val errorCode: ErrorCode
) : RuntimeException()
