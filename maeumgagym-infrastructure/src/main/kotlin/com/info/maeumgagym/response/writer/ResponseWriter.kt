package com.info.maeumgagym.response.writer

import javax.servlet.http.HttpServletResponse

interface ResponseWriter {

    fun setDefaultSetting(response: HttpServletResponse, status: Int): HttpServletResponse

    fun setBody(response: HttpServletResponse, `object`: Any): HttpServletResponse
}
