package com.info.maeumgagym.infrastructure.filter.initial

import com.info.maeumgagym.infrastructure.filter.global.GlobalFilterChain
import com.info.maeumgagym.infrastructure.threadsafe.ThreadValue
import javax.servlet.Filter
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class InitialFilterChain(
    private val filters: Map<String, Filter>
) : GlobalFilterChain {

    private var currentFilterIndex: ThreadValue<Int>? = null

    override fun doFilter(request: ServletRequest, response: ServletResponse) {
        if (currentFilterIndex == null) {
            currentFilterIndex = ThreadValue.createWith(0)
        }

        if (filters.size == currentFilterIndex!!.getValue()) {
            currentFilterIndex!!.destroy()
        }

        filters[filters.keys.toList()[currentFilterIndex!!.getValue()]]!!.doFilter(request, response, this)
    }

    override fun getFilterList(): Map<String, Filter> = filters
}
