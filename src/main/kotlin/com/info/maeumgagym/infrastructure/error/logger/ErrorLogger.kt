package com.info.maeumgagym.infrastructure.error.logger

import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo

interface ErrorLogger {

    fun printLog(errorInfo: ErrorInfo)
}
