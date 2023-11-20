package com.info.maeumgagym.common.exception

import com.info.maeumgagym.common.exception.ErrorCode
import java.lang.RuntimeException

abstract class MaeumGaGymException(
    val errorCode: ErrorCode
) : RuntimeException()
