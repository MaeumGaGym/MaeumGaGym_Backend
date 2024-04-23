package com.info.maeumgagym.infrastructure.error.filter.filterchain

import com.info.maeumgagym.infrastructure.filter.chained.ChainedFilterChainProxy

/**
 * [ExceptionChainedFilterChain]을 다른 FilterChain에 삽입하기 위한 프록시
 *
 * @see ExceptionChainedFilterChain
 *
 * @author Daybreak312
 * @since 21-03-2024
 */
class ExceptionChainedFilterChainProxy(
    override val filterChain: ExceptionChainedFilterChain
) : ChainedFilterChainProxy<ExceptionChainedFilterChain>()
