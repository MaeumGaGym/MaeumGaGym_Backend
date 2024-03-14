package com.info.maeumgagym.response.writer

import javax.servlet.http.HttpServletResponse

/**
 * [HttpServletResponseWriter]의 기본 타입.
 *
 * 특별한 이유가 없는 한 이 클래스를 사용할 것을 권고.
 *
 * @author Daybreak312
 * @since 12-03-2024
 */
abstract class DefaultHttpServletResponseWriter : HttpServletResponseWriter {

    /**
     * 인자로 받은 [object]를 JSON 데이터로 변환 후 [HttpServletResponse.getWriter]를 통해 Body를 설정.
     *
     * @author Daybreak312
     * @since 12-03-2024
     */
    abstract override fun setBody(response: HttpServletResponse, `object`: Any): HttpServletResponse

    /**
     * [HttpServletResponse]의 [HttpServletResponse.setStatus], [HttpServletResponse.setCharacterEncoding], [HttpServletResponse.setContentType]을 진행.
     *
     * 인자로 받은 [HttpServletResponse.setStatus]를 [status]로 설정.
     * [HttpServletResponse.setCharacterEncoding]를 "UTF-8"로 설정.
     * [HttpServletResponse.setContentType]을 "application/json"으로 설정.
     *
     * @author Daybreak312
     * @since 12-03-2024
     */
    abstract override fun doDefaultSettingWithStatusCode(
        response: HttpServletResponse,
        status: Int
    ): HttpServletResponse
}
