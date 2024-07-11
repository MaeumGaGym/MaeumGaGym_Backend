package com.info.maeumgagym.infrastructure.error.resolver

import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface ErrorResolver {

    fun resolve(
        errorInfo: ErrorInfo,
        request: HttpServletRequest,
        response: HttpServletResponse
    )
}
