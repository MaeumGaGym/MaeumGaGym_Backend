package com.info.maeumgagym.filter.chained

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.filter.global.GlobalFilterChain
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

/**
 * 필터 안에서 다음 필터를 호출해야하는 FilerChain을 위한 클래스
 *
 * [ApplicationFilterChain][org.apache.catalina.core.ApplicationFilterChain] 안에서 다른 FilterChain이 호출 되는 경우, 해당 FilterChain의 Filter들은 다음 필터(FilterChain 밖)를 자신의 안에서 호출하는 것이 아닌, FilterChain 바깥의 임의의 객체가 호출하게 됨
 *
 * 예를 들어, FilterA -> FilterChain -> FilterB 의 구조를 가지고 있다고 할 때, 논리적으로 아래의 코드를 실행한 것과 같이 작동
 * ```
 * FilterA.doFilter() {
 *     FilterChain.doFilter() {
 *         FilterChainInnerFilterA.doFilter()
 *         FilterChainInnerFilterB.doFilter()
 *     }
 *     FilterB.doFilter()
 * }
 * ```
 * 하지만, try ~ catch문으로 감싸 다음 Filter를 실행해야하는 경우와 같이, 위와 같은 형태로 실행되면 안되는 경우가 존재
 *
 * 이를 해결하기 위해, FilterChain에게 현재 자신을 호출한 FilterChain을 넘겨, FilterChain의 마지막에서 자신을 호출한 FilterChain을 호출하게 해 연계되어 작동되도록 구현함
 *
 * 따라서, 앞서 언급한 구조에서 논리적으로 아래의 코드를 실행한 것과 같이 작동
 * ```
 * FilterA.doFilter() {
 *     FilterChain.doFilter() {
 *         FilterChainInnerFilterA.doFilter()
 *         FilterChainInnerFilterB.doFilter() {
 *             FilterB.doFilter()
 *         }
 *     }
 * }
 * ```
 *
 * @see GlobalFilterChain
 * @see ChainedFilterChainProxy
 *
 * @author Daybreak312
 * @since 21-03-2024
 */
abstract class ChainedFilterChain : GlobalFilterChain {

    protected var calledFilterChain: ThreadLocal<FilterChain?> = ThreadLocal()

    protected var currentFilterIndex: ThreadLocal<Int> = ThreadLocal.withInitial { -1 }

    protected abstract val filters: Map<String, Filter>

    /**
     * 외부에서 이 FilterChain을 동작시킬 때 사용하는 메소드
     *
     * @param calledFilterChain 이 FilterChain을 호출한 FilterChain
     */
    fun doFilterChained(request: ServletRequest, response: ServletResponse, calledFilterChain: FilterChain) {
        this.calledFilterChain.set(calledFilterChain)

        doFilter(request, response)

        resetFilterChain()
    }

    /**
     * FilterChain 내부의 Filter들이 호출하는 메소드
     */
    final override fun doFilter(request: ServletRequest, response: ServletResponse) {
        if (calledFilterChain.get() == null) {
            throw CriticalException(500, "ChainedFilterChain called with doesn't init CalledFilterChain")
        }

        plusCurrentFilterIndex()

        if (currentFilterIndex.get() == filters.size - 1) {
            getCurrentFilter().doFilter(request, response, calledFilterChain.get())
        } else {
            getCurrentFilter().doFilter(request, response, this)
        }
    }

    /**
     * FilterChain이 끝난 후, 다시 초기 상태로 되돌리는 메소드
     */
    private fun resetFilterChain() {
        calledFilterChain.remove()
        currentFilterIndex.set(-1)
    }

    /**
     * FilterChain의 다음 Filter로 넘어가기 위해, 실행한 Filter의 Index를 담은 변수에 1 추가
     */
    private fun plusCurrentFilterIndex(): Int {
        currentFilterIndex.set(this.currentFilterIndex.get() + 1)
        return currentFilterIndex.get()
    }

    /**
     * @return [getCurrentFilterIndex]를 기반으로 현재 실행한 Filter를 반환
     */
    private fun getCurrentFilter(): Filter =
        filters[filters.keys.toList()[currentFilterIndex.get()]]!!
}
