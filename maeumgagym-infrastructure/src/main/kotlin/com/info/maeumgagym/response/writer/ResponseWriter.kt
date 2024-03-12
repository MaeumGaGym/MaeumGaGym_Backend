package com.info.maeumgagym.response.writer

import javax.servlet.http.HttpServletResponse

/**
 * [HttpServletResponse]를 가공하는 저수준 구현부를 추상화 및 모듈화한 인터페이스.
 *
 * [DefaultResponseWriter]가 기본 타입.
 * [ErrorLogResponseWriter] 등의 부가 기능을 추가한 하위 타입이 존재.
 *
 * 하위 abstract class는 private 접근 제어자를 가진 구현체가 따로 존재.
 * 자세한 것은 해당 클래스의 Docs 참고.
 *
 * 하위 타입을 작성할 때에는 abstract class로 타입을 선언하고, impl 패키지에 실제 private 구현체를 작성.
 *
 * 이 인터페이스는 하위 Bean이 많아 사용하지 않을 것을 권고.
 *
 * @see DefaultResponseWriter
 * @see ErrorLogResponseWriter
 *
 * @author Daybreak312
 * @since 12-03-2024
 */
interface ResponseWriter {

    fun setBody(response: HttpServletResponse, `object`: Any): HttpServletResponse

    fun setDefaultSetting(response: HttpServletResponse, status: Int): HttpServletResponse
}
