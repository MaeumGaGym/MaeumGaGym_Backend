package com.info.maeumgagym.error.filter.filterchain

import com.info.maeumgagym.filter.chained.ChainedFilterChain
import javax.servlet.Filter
import javax.servlet.FilterChain

class ExceptionChainedFilterChain(
    private val filters: Map<String, Filter>,
) : ChainedFilterChain() {

    private var calledFilterChain: FilterChain? = null

    private var currentFilterIndex: Int = -1

    override fun getFilters(): Map<String, Filter> = filters

    override fun getCalledFilterChain(): FilterChain? = calledFilterChain

    override fun getCurrentFilterIndex(): Int = this.currentFilterIndex

    override fun plusCurrentFilterIndex(): Int {
        this.currentFilterIndex += 1
        return this.currentFilterIndex
    }

    override fun resetFilterChain() {
        this.calledFilterChain = null
        this.currentFilterIndex = -1
    }

    override fun setCalledFilterChain(filterChain: FilterChain) {
        this.calledFilterChain = filterChain
    }
}
