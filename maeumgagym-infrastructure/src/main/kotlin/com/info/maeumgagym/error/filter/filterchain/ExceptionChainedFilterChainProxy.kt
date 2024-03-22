package com.info.maeumgagym.error.filter.filterchain

import com.info.maeumgagym.filter.chained.ChainedFilterChainProxy

class ExceptionChainedFilterChainProxy(
    exceptionChainedFilterChain: ExceptionChainedFilterChain
) : ChainedFilterChainProxy<ExceptionChainedFilterChain>() {

    override val filterChain: ExceptionChainedFilterChain = exceptionChainedFilterChain
}
