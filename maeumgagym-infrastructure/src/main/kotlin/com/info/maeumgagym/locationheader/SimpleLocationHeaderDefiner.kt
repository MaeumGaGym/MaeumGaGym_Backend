package com.info.maeumgagym.locationheader

import com.info.maeumgagym.controller.common.locationheader.LocationHeaderSubjectDefiner
import org.springframework.stereotype.Component

@Component
class SimpleLocationHeaderDefiner : LocationHeaderSubjectDefiner {

    private var id: ThreadLocal<String> = ThreadLocal()
    override fun setSubject(subject: Any) {
        this.id.set(subject.toString())
    }

    override fun getSubject(): String? {
        return this.id.get()
    }

    override fun removeSubject() {
        this.id.remove()
    }
}
