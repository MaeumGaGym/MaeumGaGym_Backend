package com.info.maeumgagym.locationheader

import com.info.maeumgagym.controller.common.locationheader.LocationHeaderSubjectDefiner
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 필요에 따라 [HttpServletResponse]에 Location Header를 작성하는 [HandlerInterceptor]
 * - [HttpMethod.POST] 혹은 [HttpMethod.PUT]일 경우 작성
 * - Login과 같이 Location Header를 작성할 수 없는 경우 제외
 */
@Component
class LocationHeaderInterceptor(
    private val dispatcherServlet: DispatcherServlet,
    private val locationHeaderSubjectDefiner: LocationHeaderSubjectDefiner
) : HandlerInterceptor {

    private val checkedStatusCodes = listOf(HttpStatus.OK, HttpStatus.CREATED)

    /**
     * Location Header를 작성하기로 예약된 [HttpMethod]들
     */
    private val checkedMethods = listOf(HttpMethod.POST, HttpMethod.PUT)

    private val uncheckedURIs = listOf(
        "/auth", "/apple", "/kakao", "/google",
        "/report",
        "/step",
        "/public",
        "/waka"
    )

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean =
        true

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {

        if (!isCheckedStatusCodeResponse(response)) {
            return
        }

        if (!isCheckedMethodRequest(request)
            || isUncheckedURIRequest(request)
        ) {
            return
        }

        if (isPutRequest(request)) {
            response.setLocationHeader(request.requestURL.toString())
        } else {
            if (locationHeaderSubjectDefiner.getSubject() == null) {
                return
            }
            response.setLocationHeader("${request.requestURL}/${locationHeaderSubjectDefiner.getSubject()}")
        }

        locationHeaderSubjectDefiner.removeSubject()
    }

    /**
     * Location Header를 작성하기로 예약된 StatusCode([HttpStatus])인지 확인
     */
    private fun isCheckedStatusCodeResponse(response: HttpServletResponse): Boolean =
        checkedStatusCodes.contains(HttpStatus.valueOf(response.status))

    /**
     * Location Header를 작성하기로 예약된 Method([HttpMethod])인지 확인
     */
    private

    fun isCheckedMethodRequest(request: HttpServletRequest): Boolean =
        checkedMethods.contains(HttpMethod.valueOf(request.method))

    /**
     * Location Header를 작성하지 않기로 예약된 URI인지 확인
     */
    private fun isUncheckedURIRequest(request: HttpServletRequest): Boolean {
        uncheckedURIs.forEach {
            if (request.requestURI.startsWith(it)) return true
        }
        return false
    }

    private fun isPutRequest(request: HttpServletRequest): Boolean =
        request.method.uppercase() == HttpMethod.PUT.name

    private fun HttpServletResponse.setLocationHeader(content: String) {
        this.setHeader(HttpHeaders.LOCATION, content)
    }
}
