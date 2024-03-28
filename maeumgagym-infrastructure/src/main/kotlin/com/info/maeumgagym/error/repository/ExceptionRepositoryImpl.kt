package com.info.maeumgagym.error.repository

import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재
 *
 * @author Daybreak312
 * @since 27-03-2024
 */
@Component
private class ExceptionRepositoryImpl : ExceptionRepository {

    private val throwable: ThreadLocal<Exception?> = ThreadLocal.withInitial { null }

    override fun save(e: Exception) {
        this.throwable.set(e)
    }

    override fun get() {
        this.throwable.get()?.run {
            this@ExceptionRepositoryImpl.throwable.remove()
            throw this
        }
    }
}
