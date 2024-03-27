package com.info.maeumgagym.error.repository

import org.springframework.stereotype.Component

@Component
private class ExceptionRepositoryImpl : ExceptionRepository {

    private val throwable: ThreadLocal<Exception?> = ThreadLocal.withInitial { null }

    override fun save(e: Exception) {
        this.throwable.set(e)
    }

    override fun throwIt() {
        this.throwable.get()?.run {
            this@ExceptionRepositoryImpl.throwable.remove()
            throw this
        }
    }
}
