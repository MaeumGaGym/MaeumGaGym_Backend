package com.info.maeumgagym.infrastructure.error.resolver

import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 에러에 대한 처리를 진행
 *
 * 여러 개의 하위 구현체가 존재할 수 있음.
 *
 * @author Daybreak312
 * @since 11-07-2024
 */
interface ErrorResolver {

    fun resolve(
        errorInfo: ErrorInfo,
        request: HttpServletRequest,
        response: HttpServletResponse
    )
}
