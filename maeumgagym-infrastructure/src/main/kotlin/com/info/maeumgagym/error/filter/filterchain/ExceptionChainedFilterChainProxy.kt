package com.info.maeumgagym.error.filter.filterchain

import com.info.maeumgagym.filter.chained.ChainedFilterChain
import com.info.maeumgagym.filter.chained.ChainedFilterChainProxy

class ExceptionChainedFilterChainProxy(
    private val exceptionChainedFilterChain: ExceptionChainedFilterChain
) : ChainedFilterChainProxy() {

    override fun getFilterChain(): ChainedFilterChain = this.exceptionChainedFilterChain
}
