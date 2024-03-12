package com.info.maeumgagym.response.writer

import javax.servlet.http.HttpServletResponse

/**
 * [HttpServletResponse]를 가공하는 저수준 구현부를 추상화 및 모듈화한 인터페이스.
 *
 * [DefaultHttpServletResponseWriter]가 기본 타입.
 * [ErrorLogHttpServletResponseWriter] 등의 부가 기능을 추가한 하위 타입이 존재.
 *
 * 하위 abstract class는 private 접근 제어자를 가진 구현체가 따로 존재.
 * 자세한 것은 해당 클래스의 Docs 참고.
 *
 * 하위 타입을 작성할 때에는 abstract class로 타입을 선언하고, impl 패키지에 실제 private 구현체를 작성.
 *
 * 이 인터페이스는 하위 Bean이 많아 사용하지 않을 것을 권고.
 *
 * | Daybreak312
 *
 * HttpServletResponse에 직접 접근해 정보를 수정할 때에는 EncodingType, ContentType 등 기본적인 설정이 많았습니다.
 *
 * 이러한 설정들을 한 곳에 모은 모듈이 존재하지 않아 동일한 코드가 애플리케이션 전체에 걸쳐 몇몇 존재합니다.
 *
 * 이러한 코드를 통합하기 위해 해당 모듈을 작성했으며, 지속적으로 기능을 추가하고 보완할 예정입니다.
 *
 * | Daybreak312
 *
 * @see DefaultHttpServletResponseWriter
 * @see ErrorLogHttpServletResponseWriter
 *
 * @author Daybreak312
 * @since 12-03-2024
 */
interface HttpServletResponseWriter {

    fun setBody(response: HttpServletResponse, `object`: Any): HttpServletResponse

    fun doDefaultSettingWithStatusCode(response: HttpServletResponse, status: Int): HttpServletResponse
}
