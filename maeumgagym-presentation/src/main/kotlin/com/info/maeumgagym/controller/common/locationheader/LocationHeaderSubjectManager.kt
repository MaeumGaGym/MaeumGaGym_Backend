package com.info.maeumgagym.controller.common.locationheader

import org.springframework.http.HttpMethod

/**
 * 주로 [HttpMethod.POST] 요청에서, Location Header로 포함되는, GET 요청을 위해 사용할 id 혹은 key값(subject)를 할당하고 불러오기 위한 관리자
 *
 * @author Daybreak312
 * @since 08-03-2024
 */
interface LocationHeaderSubjectManager {

    fun setSubject(subject: Any)

    fun getSubject(): String?

    fun removeSubject()
}
