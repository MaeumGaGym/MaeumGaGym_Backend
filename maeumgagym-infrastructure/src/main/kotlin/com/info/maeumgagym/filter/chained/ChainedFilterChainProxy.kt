package com.info.maeumgagym.filter.chained

import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

abstract class ChainedFilterChainProxy : GenericFilterBean() {

    abstract fun getFilterChain(): ChainedFilterChain

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val filterChain = this.getFilterChain()
        filterChain.doFilterChained(request, response, chain)
    }
}
