package com.info.maeumgagym.infrastructure.filter.config

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.infrastructure.error.filter.ErrorLogResponseFilter
import com.info.maeumgagym.infrastructure.error.filter.ExceptionConvertFilter
import com.info.maeumgagym.infrastructure.error.filter.filterchain.ExceptionChainedFilterChain
import com.info.maeumgagym.infrastructure.error.filter.filterchain.ExceptionChainedFilterChainProxy
import com.info.maeumgagym.infrastructure.error.logger.ErrorLogger
import com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository
import com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext
import com.info.maeumgagym.infrastructure.request.filter.CurrentRequestContextFilter
import com.info.maeumgagym.infrastructure.response.writer.DefaultHttpServletResponseWriter
import com.info.maeumgagym.infrastructure.response.writer.ErrorLogHttpServletResponseWriter
import com.info.maeumgagym.security.cors.filter.CorsHeaderGenerateFilter
import org.apache.tomcat.websocket.server.WsFilter
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.filter.DelegatingFilterProxy
import javax.servlet.Filter

/**
 * [ApplicationFilterChain][org.apache.catalina.core.ApplicationFilterChain] 속 Filter들의 삽입과 순서 설정
 *
 * @see SecurityFilterChainConfig
 *
 * @author Daybreak312
 * @since 27-02-2024
 */
@Configuration
class ApplicationFilterChainConfig(
    private val defaultHttpServletResponseWriter: DefaultHttpServletResponseWriter,
    private val errorLogHttpServletResponseWriter: ErrorLogHttpServletResponseWriter,
    private val errorLogger: ErrorLogger,
    private val exceptionRepository: ExceptionRepository,
    private val currentRequestContext: CurrentRequestContext,
    private val applicationContext: ApplicationContext
) {

    /**
     * FilterChain에 등록할 [Filter]를 명시해두는 list.
     *
     * 명시된 순서에 따라 [Filter]를 등록할 것.
     *
     * [getFilterOrder] 메소드를 이용해 정수형 순서 정보를 얻을 수 있음
     */
    private val filtersOrderList = listOf<Class<out Filter>>(
        CharacterEncodingFilter::class.java,
        WebMvcMetricsFilter::class.java,
        OrderedFormContentFilter::class.java,
        OrderedRequestContextFilter::class.java,
        ExceptionChainedFilterChainProxy::class.java,
        CurrentRequestContextFilter::class.java,
        CorsHeaderGenerateFilter::class.java,
        DelegatingFilterProxy::class.java,
        WsFilter::class.java
    )

    //@Bean
    fun characterEncodingFilterConfig(): FilterRegistrationBean<CharacterEncodingFilter> {
        return commonFilterDefaultSetting(CharacterEncodingFilter::class.java)
    }

    //@Bean
    fun webMvcMetricsFilterConfig(): FilterRegistrationBean<WebMvcMetricsFilter> {
        return commonFilterDefaultSetting(WebMvcMetricsFilter::class.java)
    }

    //@Bean
    fun orderedFormContentFilterConfig(): FilterRegistrationBean<OrderedFormContentFilter> {
        return commonFilterDefaultSetting(OrderedFormContentFilter::class.java)
    }

    //@Bean
    fun orderedRequestContextFilterConfig(): FilterRegistrationBean<OrderedRequestContextFilter> {
        return commonFilterDefaultSetting(OrderedRequestContextFilter::class.java)
    }

    @Bean
    fun exceptionChainedFilterChainProxyConfig(): FilterRegistrationBean<ExceptionChainedFilterChainProxy> {
        val filterChain = ExceptionChainedFilterChain(
            mapOf(
                Pair(
                    ErrorLogResponseFilter::class.simpleName!!,
                    ErrorLogResponseFilter(
                        defaultHttpServletResponseWriter,
                        errorLogHttpServletResponseWriter,
                        errorLogger
                    )
                ),
                Pair(
                    ExceptionConvertFilter::class.simpleName!!,
                    ExceptionConvertFilter(
                        exceptionRepository
                    )
                )
            )
        )

        val bean = FilterRegistrationBean(
            ExceptionChainedFilterChainProxy(filterChain)
        )

        return filterBeanInfoDefaultSetting(bean)
    }

    @Bean
    fun currentRequestContextFilterConfig(): FilterRegistrationBean<CurrentRequestContextFilter> {
        val bean = FilterRegistrationBean(
            CurrentRequestContextFilter(currentRequestContext)
        )

        return filterBeanInfoDefaultSetting(bean)
    }

    @Bean
    fun corsHeaderGenerateFilter(): FilterRegistrationBean<CorsHeaderGenerateFilter> {
        val bean = FilterRegistrationBean(
            CorsHeaderGenerateFilter()
        )

        return filterBeanInfoDefaultSetting(bean)
    }

    // SecurityFilterChain
    //@Bean
    fun delegatingFilterProxyConfig(): FilterRegistrationBean<DelegatingFilterProxy> {
        return commonFilterDefaultSetting(DelegatingFilterProxy::class.java)
    }

    //@Bean
    fun wsFilterConfig(): FilterRegistrationBean<WsFilter> {
        return commonFilterDefaultSetting(WsFilter::class.java)
    }

    private fun <T : Filter> commonFilterDefaultSetting(`class`: Class<out T>): FilterRegistrationBean<T> {
        val filter = applicationContext.getBean(`class`)

        val bean = FilterRegistrationBean(filter)

        return filterBeanInfoDefaultSetting(bean)
    }

    private fun <T : Filter> filterBeanInfoDefaultSetting(bean: FilterRegistrationBean<T>): FilterRegistrationBean<T> {
        bean.order = getFilterOrder(bean.filter::class.java)
        bean.addUrlPatterns("/*")

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
            throw CriticalException("Attempted registration of Unknown filter")
        }

        return index + Int.MIN_VALUE
    }
}
