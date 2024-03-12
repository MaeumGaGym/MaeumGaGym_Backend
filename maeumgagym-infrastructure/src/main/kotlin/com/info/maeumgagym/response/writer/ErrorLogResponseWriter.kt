package com.info.maeumgagym.response.writer

import com.info.maeumgagym.error.log.ErrorLog
import javax.servlet.http.HttpServletResponse

abstract class ErrorLogResponseWriter : ResponseWriter {

    abstract fun writeResponseWithErrorLogAndException(
        response: HttpServletResponse,
        errorLog: ErrorLog,
        e: Exception
    ): HttpServletResponse
}
