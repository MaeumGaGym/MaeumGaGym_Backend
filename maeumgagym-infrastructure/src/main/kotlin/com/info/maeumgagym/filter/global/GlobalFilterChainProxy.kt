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
 * @param T 해당 Proxy 클래스가 대리하고 있는 FilterChain
 *
 * @author Daybreak312
 * @since 21-03-2024
 */
abstract class GlobalFilterChainProxy<out T : GlobalFilterChain> : GenericFilterBean() {

    protected abstract val filterChain: T

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        this.filterChain.doFilter(request, response)
    }
}
