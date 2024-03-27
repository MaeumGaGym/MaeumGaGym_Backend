package com.info.maeumgagym.error.filter.filterchain

import com.info.maeumgagym.filter.chained.ChainedFilterChain
import javax.servlet.Filter

class ExceptionChainedFilterChain(
    override val filters: Map<String, Filter>
) : ChainedFilterChain() {

    override fun getFilterList(): Map<String, Filter> = filters
}
