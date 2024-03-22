package com.info.maeumgagym.filter.chained

import com.info.maeumgagym.filter.global.GlobalFilterChainProxy

abstract class ChainedFilterChainProxy<out T : ChainedFilterChain> : GlobalFilterChainProxy<T>() {

    abstract override val filterChain: T
}
