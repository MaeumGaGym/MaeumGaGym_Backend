package com.info.maeumgagym.error.filter.filterchain

import com.info.maeumgagym.filter.chained.ChainedFilterChain
import javax.servlet.Filter
import javax.servlet.FilterChain

class ExceptionChainedFilterChain(
    private val filters: Map<String, Filter>
) : ChainedFilterChain() {

    private var calledFilterChain: ThreadLocal<FilterChain>? = null

    private var currentFilterIndex: ThreadLocal<Int> = ThreadLocal.withInitial {
        -1
    }

    override fun getFilters(): Map<String, Filter> = filters

    override fun getCalledFilterChain(): FilterChain? = calledFilterChain?.get()

    override fun getCurrentFilterIndex(): Int = this.currentFilterIndex.get()

    override fun plusCurrentFilterIndex(): Int {
        this.currentFilterIndex.set(this.getCurrentFilterIndex() + 1)
        return this.currentFilterIndex.get()
    }

    override fun resetFilterChain() {
        this.calledFilterChain = null
        this.currentFilterIndex = ThreadLocal.withInitial { -1 }
    }

    override fun setCalledFilterChain(filterChain: FilterChain) {
        this.calledFilterChain = ThreadLocal.withInitial { filterChain }
    }
}
