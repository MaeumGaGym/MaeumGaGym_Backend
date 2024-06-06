package com.info.maeumgagym.infrastructure.filter.initial

import com.info.maeumgagym.infrastructure.filter.global.GlobalFilterChainProxy
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class InitialFilterChainProxy(
    override val filterChain: InitialFilterChain
) : GlobalFilterChainProxy<InitialFilterChain>() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        filterChain.doFilter(request, response)
    }
}
