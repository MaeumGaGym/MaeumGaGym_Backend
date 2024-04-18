package com.info.maeumgagym.error.repository

/**
 * 생성된 Exception을 저장해두었다가 다른 위치에서 이용할 수 있도록 Thread에 전역적으로 저장하기 위한 인터페이스
 *
 * 주로, [ExceptionConvertFilter][com.info.maeumgagym.error.filter.ExceptionConvertFilter]에서 Exception들을 [MaeumgagymException][com.info.maeumgagym.common.exception.MaeumGaGymException] 또는 그 하위 타입으로 변환하기 위해 사용
 *
 * @author Daybreak312
 * @since 27-03-2024
 */
interface ExceptionRepository {

    /**
     * Exception을 저장
     */
    fun save(e: Exception)

    /**
     * 저장되어 있던 Exception을 thorw
     *
     * 저장되어 있지 않으면 아무것도 throw하지 않음
     *
     * @throws Exception 저장되어 있던 Exception
     */
    @Throws(Exception::class)
    fun throwIt()
}
