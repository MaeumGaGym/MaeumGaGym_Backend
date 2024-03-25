package com.info.maeumgagym.filter.global

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * 특정한 역할이 없는, 해당 애플리케이션에서 사용되는 기본 FilterChain
 *
 * @see GlobalFilterChainProxy
 *
 * @author Daybreak312
 * @since 21-03-2024
 */
interface GlobalFilterChain : FilterChain {

    /**
     * FilterChain 내부의 Filter들을 순회 작동
     */
    override fun doFilter(request: ServletRequest, response: ServletResponse)

    /**
     * FilterChain에 포함된 Filter들을 반환
     *
     * @return Filter의 BeanName 혹은 ClassName과 Filter 객체의 Map 컬렉션
     */
    fun getFilters(): Map<String, Filter>
}
