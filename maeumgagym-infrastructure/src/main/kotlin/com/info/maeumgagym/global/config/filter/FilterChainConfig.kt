package com.info.maeumgagym.global.config.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.global.error.filter.ErrorLogResponseFilter
import com.info.maeumgagym.global.error.filter.ExceptionConvertFilter
import com.info.maeumgagym.global.error.log.manager.ErrorLogManager
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.filter.DelegatingFilterProxy
import javax.servlet.Filter

@Configuration
class FilterChainConfig(
    private val objectMapper: ObjectMapper,
    private val errorLogManager: ErrorLogManager
) {

    /**
     * FilterChain에 등록할 [Filter]를 명시해두는 list.
     *
     * 명시된 순서에 따라 [Filter]를 등록할 것.
     *
     * [getFilterOrder] 메소드를 이용해 정수형 순서 정보를 얻을 수 있음
     */
    private val filtersOrderList = listOf<Class<out Filter>>(
        ErrorLogResponseFilter::class.java,
        ExceptionConvertFilter::class.java,
        DelegatingFilterProxy::class.java
    )

    @Bean
    fun errorLogResponseFilterConfig(): FilterRegistrationBean<ErrorLogResponseFilter> {
        val bean = FilterRegistrationBean(
            ErrorLogResponseFilter(objectMapper, errorLogManager)
        )

        bean.addUrlPatterns("/*")
        bean.order = getFilterOrder(ErrorLogResponseFilter::class.java)

        return bean
    }

    @Bean
    fun exceptionConvertFilterConfig(): FilterRegistrationBean<ExceptionConvertFilter> {
        val bean = FilterRegistrationBean(
            ExceptionConvertFilter()
        )

        bean.addUrlPatterns("/*")
        bean.order = getFilterOrder(ExceptionConvertFilter::class.java)

        return bean
    }

    /**
     * [SecurityFilterChain]에 대한 순서 설정.
     *
     * 보다 정확히는, [SecurityFilterChain]을 감싼 [DelegatingFilterProxy]의 설정.
     */
    @Bean
    fun springSecurityFilterChainOrderConfig(): FilterRegistrationBean<DelegatingFilterProxy> {
        val bean = FilterRegistrationBean(
            DelegatingFilterProxy("springSecurityFilterChain")
        )

        bean.order = getFilterOrder(DelegatingFilterProxy::class.java)

        return bean
    }

    /**
     * [filtersOrderList]에 등록된 [Filter] 순서의 정수형 정보 반환.
     *
     * 만약 순서 정보를 얻으려는 [Filter]가 [filtersOrderList]에 등록되어 있지 않을 경우 [CriticalException] 발생.
     */
    private fun getFilterOrder(`class`: Class<out Filter>): Int {
        val index = this.filtersOrderList.indexOf(`class`)

        if (index == -1) {
            throw CriticalException(500, "Attempted registration of Unknown filter")
        }

        return index
    }
}
