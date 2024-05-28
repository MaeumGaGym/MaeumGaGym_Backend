package com.info.maeumgagym.infrastructure.filter.initial

import com.info.maeumgagym.infrastructure.filter.global.GlobalFilterChain
import javax.servlet.Filter
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class InitialFilterChain(
    private val filters: Map<String, Filter>
) : GlobalFilterChain {

    private val currentFilterIndex: ThreadLocal<Int?> = ThreadLocal.withInitial { null }

    override fun doFilter(request: ServletRequest, response: ServletResponse) {
        if (currentFilterIndex.get() == null) {
            currentFilterIndex.set(0)
        }

        if (filters.size == currentFilterIndex.get()) {
            currentFilterIndex.remove()
            return
        }

        val filter = filters[filters.keys.toList()[currentFilterIndex.get()!!]]!!
        currentFilterIndex.set(currentFilterIndex.get()!! + 1)
        filter.doFilter(request, response, this)
    }

    override fun getFilterList(): Map<String, Filter> = filters
}
