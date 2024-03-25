package com.info.maeumgagym.filter.chained

import com.info.maeumgagym.filter.global.GlobalFilterChainProxy
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

abstract class ChainedFilterChainProxy<out T : ChainedFilterChain> : GlobalFilterChainProxy<T>() {

    abstract override val filterChain: T

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        this.filterChain.doFilterChained(request, response, chain)
    }
}
