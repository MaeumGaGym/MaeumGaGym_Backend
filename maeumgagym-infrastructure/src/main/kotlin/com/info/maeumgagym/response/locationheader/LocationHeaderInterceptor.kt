package com.info.maeumgagym.response.locationheader

import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.controller.common.locationheader.LocationHeaderManager
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 필요에 따라 [HttpServletResponse]에 Location Header를 작성하는 [HandlerInterceptor]
 * - [HttpMethod.POST] 혹은 [HttpMethod.PUT]일 경우 작성
 * - Login과 같이 Location Header를 작성할 수 없는 경우 제외
 *
 * *[HttpMethod.POST]의 경우 필요한 추가적인 처리*
 * 1. Service에서 [LocationSubjectDto]를 반환
 * 2. Controller에서 [LocationHeaderManager]의 [setSubject][LocationHeaderManager.setSubject]를 통해 LocationHeader에서 사용할 id 혹은 key값을 지정
 *
 * @author Daybreak312
 * @since 08-03-2024
 */
class LocationHeaderInterceptor(
    private val locationHeaderManager: LocationHeaderManager
) : HandlerInterceptor {

    private val checkedStatusCodes = listOf(HttpStatus.OK, HttpStatus.CREATED)

    /**
     * Location Header를 작성하기로 예약된 [HttpMethod]들
     */
    private val checkedMethods = listOf(HttpMethod.POST, HttpMethod.PUT)

    private val uncheckedURIs = listOf(
        "/auth",
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
        if (!isCheckedStatusCodeResponse(response) ||
            !isCheckedMethodRequest(request) ||
            isUncheckedURIRequest(request)
        ) {
            return
        }

        if (isPutRequest(request)) {
            response.setLocationHeader(request.requestURL.toString())
            return
        }

        if (locationHeaderManager.getSubject() == null &&
            locationHeaderManager.getURI() == null
        ) {
            response.status = HttpStatus.NO_CONTENT.value()
            return
        }

        response.setLocationHeader(getLocationHeaderContent(request))

        locationHeaderManager.clear()
    }

    /**
     * Location Header를 작성하기로 예약된 StatusCode([HttpStatus])인지 확인
     */
    private fun isCheckedStatusCodeResponse(response: HttpServletResponse): Boolean =
        checkedStatusCodes.contains(HttpStatus.valueOf(response.status))

    /**
     * Location Header를 작성하기로 예약된 Method([HttpMethod])인지 확인
     */
    private fun isCheckedMethodRequest(request: HttpServletRequest): Boolean =
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

    private fun getLocationHeaderContent(request: HttpServletRequest): String =
        if (locationHeaderManager.getSubject() == null) {
            request.requestURL.substring(
                0,
                request.requestURL.length - request.requestURI.length
            ) + "/maeumgagym" + locationHeaderManager.getURI()
        } else {
            "${request.requestURL}/${locationHeaderManager.getSubject()!!}"
        }

    private fun HttpServletResponse.setLocationHeader(content: String) {
        this.setHeader(HttpHeaders.LOCATION, content)
    }
}
