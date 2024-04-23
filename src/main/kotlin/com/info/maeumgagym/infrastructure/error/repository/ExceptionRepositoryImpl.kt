package com.info.maeumgagym.error.repository

import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재
 *
 * @author Daybreak312
 * @since 27-03-2024
 */
@Component
private class ExceptionRepositoryImpl : com.info.maeumgagym.infrastructure.error.repository.ExceptionRepository {

    private val throwable: ThreadLocal<Exception?> = ThreadLocal.withInitial { null }

    override fun save(e: Exception) {
        this.throwable.set(e)
    }

    @Throws(Exception::class)
    override fun throwIt() {
        this.throwable.get()?.apply {
            this@ExceptionRepositoryImpl.throwable.remove()
            throw this
        }
    }
}
