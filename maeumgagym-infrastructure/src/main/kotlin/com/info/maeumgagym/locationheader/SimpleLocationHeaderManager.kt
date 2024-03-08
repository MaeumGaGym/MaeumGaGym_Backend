package com.info.maeumgagym.locationheader

import com.info.maeumgagym.controller.common.locationheader.LocationHeaderSubjectManager
import org.springframework.stereotype.Component

/**
 * @see LocationHeaderSubjectManager
 *
 * [LocationHeaderSubjectManager]를 구현해, [subject]를 [ThreadLocal]로 갖고 있는 클래스
 *
 * 각 스레드별로 Location Header를 위한 subject를 갖고 있음
 *
 * @author Daybreak312
 * @since 08-03-2024
 */
@Component
class SimpleLocationHeaderManager : LocationHeaderSubjectManager {

    private var subject: ThreadLocal<String> = ThreadLocal()

    override fun setSubject(subject: Any) {
        this.subject.set(subject.toString())
    }

    override fun getSubject(): String? {
        return this.subject.get()
    }

    override fun removeSubject() {
        this.subject.remove()
    }
}
