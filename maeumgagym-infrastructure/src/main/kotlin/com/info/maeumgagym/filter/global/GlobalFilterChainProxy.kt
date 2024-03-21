package com.info.maeumgagym.filter.global

import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * [GlobalFilterChain]을 [org.apache.catalina.core.ApplicationFilterChain]에 삽입하기 위한 프록시
 *
 * @see GlobalFilterChain
 *
 * @author Daybreak312
 * @since 21-03-2024
 */
abstract class GlobalFilterChainProxy : GenericFilterBean() {

    protected abstract val filterChain: GlobalFilterChain

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        filterChain.doFilter(request, response)
    }
}
