package com.info.maeumgagym.infrastructure.error.filter.filterchain

import com.info.maeumgagym.infrastructure.filter.chained.ChainedFilterChain
import javax.servlet.Filter

/**
 * 예외 처리와 관련한 Filter를 실행하기 위한 [ChainedFilterChain][com.info.maeumgagym.infrastructure.filter.chained.ChainedFilterChain]
 *
 * @see ExceptionChainedFilterChainProxy
 *
 * @author Daybreak312
 * @since 21-03-2024
 */
class ExceptionChainedFilterChain(
    override val filters: Map<String, Filter>
) : ChainedFilterChain() {

    override fun getFilterList(): Map<String, Filter> = filters
}
