package com.info.maeumgagym.filter.chained

import com.info.maeumgagym.filter.global.GlobalFilterChainProxy
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * [ChainedFilterChain]을 실행하기 위한 [FilterChainProxy][GlobalFilterChainProxy]
 *
 * @see ChainedFilterChain
 * @see GlobalFilterChainProxy
 *
 * @author Daybreak312
 * @since 21-03-2024
 */
abstract class ChainedFilterChainProxy<out T : ChainedFilterChain> : GlobalFilterChainProxy<T>() {

    abstract override val filterChain: T

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        this.filterChain.doFilterChained(request, response, chain)
    }
}
