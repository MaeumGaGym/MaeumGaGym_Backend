package com.info.maeumgagym.infrastructure.request.context

import com.info.maeumgagym.infrastructure.request.filter.CurrentRequestContextFilter
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.Executors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Transactional
@SpringBootTest
class CurrentRequestContextTests @Autowired constructor(
    private val currentRequestContext: CurrentRequestContext
) {

    private val filter = CurrentRequestContextFilter(currentRequestContext)

    fun init() {
        currentRequestContext.clear()
    }

    @Test
    fun 정상_사용() {
        init()
        val mockKRequest = mockk<HttpServletRequest>()

        currentRequestContext.setCurrentRequest(mockKRequest)

        Assertions.assertEquals(
            mockKRequest,
            currentRequestContext.getCurrentRequest()
        )
    }

    @Test
    fun 초기화되지_않은_상태에서_사용() {
        init()
        Assertions.assertThrows(NullPointerException::class.java) {
            currentRequestContext.getCurrentRequest()
        }
    }

    @Test
    fun 필터를_통해_초기화하고_사용() {
        init()
        val mockkRequest = mockk<HttpServletRequest>()
        val mockkResponse = mockk<HttpServletResponse>()
        val mockkFilterChain = mockk<FilterChain>()

        every { mockkFilterChain.doFilter(mockkRequest, mockkResponse) } just runs

        val thread: Thread = Executors.defaultThreadFactory().newThread {
            filter.doFilter(
                mockkRequest,
                mockkResponse,
                mockkFilterChain
            )

            Assertions.assertEquals(
                mockkRequest,
                currentRequestContext.getCurrentRequest()
            )
        }

        try {
            thread.start()
        } finally {
            thread.join()
        }
    }
}
