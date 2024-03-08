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
