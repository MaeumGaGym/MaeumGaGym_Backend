package com.info.maeumgagym.infrastructure.request.filter

import com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

/**
 * [CurrentRequestContext]를 초기화하기 위한 필터
 *
 * @author Daybreak312
 * @since 02-05-2024
 */
class CurrentRequestContextFilter(
    private val currentRequestContext: CurrentRequestContext
) : GenericFilterBean() {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        currentRequestContext.setCurrentRequest(
            request as HttpServletRequest
        )

        chain.doFilter(request, response)
    }
}
