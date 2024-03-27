package com.info.maeumgagym.error.repository

interface ExceptionRepository {

    fun save(e: Exception)

    @Throws(Exception::class)
    fun throwIt()
}
