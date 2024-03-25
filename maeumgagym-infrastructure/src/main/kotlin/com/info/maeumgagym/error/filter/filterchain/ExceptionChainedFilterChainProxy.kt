package com.info.maeumgagym.error.filter.filterchain

import com.info.maeumgagym.filter.chained.ChainedFilterChainProxy

class ExceptionChainedFilterChainProxy(
    override val filterChain: ExceptionChainedFilterChain
) : ChainedFilterChainProxy<ExceptionChainedFilterChain>()
