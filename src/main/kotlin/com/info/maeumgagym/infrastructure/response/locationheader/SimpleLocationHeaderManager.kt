package com.info.maeumgagym.infrastructure.response.locationheader

import com.info.maeumgagym.presentation.controller.common.locationheader.LocationHeaderManager
import org.springframework.stereotype.Component

/**
 * @see LocationHeaderManager
 *
 * [LocationHeaderManager]를 구현해, [subject]를 [ThreadLocal]로 갖고 있는 클래스
 *
 * 각 스레드별로 Location Header를 위한 subject를 갖고 있음
 *
 * @author Daybreak312
 * @since 08-03-2024
 */
@Component
class SimpleLocationHeaderManager :
    LocationHeaderManager {

    private val subject: ThreadLocal<String> = ThreadLocal()

    private val URI: ThreadLocal<String> = ThreadLocal()

    override fun getSubject(): String? {
        return this.subject.get()
    }

    override fun setSubject(subject: Any) {
        this.subject.set(subject.toString())
    }

    override fun getURI(): String? {
        return this.URI.get()
    }

    override fun setURI(uri: String) {
        this.URI.set(uri)
    }

    override fun clear() {
        this.subject.remove()
        this.URI.remove()
    }
}
