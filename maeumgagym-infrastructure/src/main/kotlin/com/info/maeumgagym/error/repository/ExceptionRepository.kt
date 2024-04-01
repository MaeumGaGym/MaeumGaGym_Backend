package com.info.maeumgagym.error.repository

/**
 * 생성된 Exception을 저장해두었다가 다른 위치에서 이용할 수 있도록 Thread에 전역적으로 저장하기 위한 인터페이스
 *
 * @author Daybreak312
 * @since 27-03-2024
 */
interface ExceptionRepository {

    fun save(e: Exception)

    @Throws(Exception::class)
    fun throwIt()
}
