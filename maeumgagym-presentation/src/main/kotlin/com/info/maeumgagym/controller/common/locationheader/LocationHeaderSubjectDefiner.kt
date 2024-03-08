package com.info.maeumgagym.controller.common.locationheader

interface LocationHeaderSubjectDefiner {

    fun setSubject(subject: Any)

    fun getSubject(): String?

    fun removeSubject()
}
