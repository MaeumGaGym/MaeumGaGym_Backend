package com.info.maeumgagym.infrastructure.error.logger

import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo

/**
 * [ErrorInfo]를 로그로 출력
 *
 * @author Daybreak312
 * @since 09-05-2024
 */
interface ErrorLogger {

    fun printLog(errorInfo: ErrorInfo)
}
