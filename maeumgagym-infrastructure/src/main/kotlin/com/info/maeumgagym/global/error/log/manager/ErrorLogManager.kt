package com.info.maeumgagym.global.error.log.manager

import com.info.maeumgagym.global.error.log.ErrorLog

/**
 * [ErrorLog]가 담겨 있는 *ErrorLogContext*의 *Manager*
 *
 * *ErrorLogContext*는 구현체인 [SimpleErrorLogManager]에 존재하며, [MutableMap]<[String], [ErrorLog]>으로 이루어짐. 또한 일종의 프록시 저장소로, [ErrorLog] 정보는 *Database*에도 저장. 프록시 개념으로써의 자세한 개념은 각 함수 설명에 존재
 * - [save]
 * - [get]
 *
 * 서버가 가동된 이후의 [ErrorLog]는 *ErrorLogContext*에 저장. 이로써 최근의 [ErrorLog]는 보다 빠르게 접근할 수 있음
 *
 * 해당 인터페이스 사용시 실제 객체는 [SimpleErrorLogManagerProxy]가 주입됨. 이 객체는 [SimpleErrorLogManager]의 *Bean*을 가지고 있으며, *Database*와의 통신은 구현되어 있지 않은 [SimpleErrorLogManager]를 대신해 *Database*와 관련된 처리를 대리
 *
 * @see SimpleErrorLogManager
 * @see SimpleErrorLogManagerProxy
 */
interface ErrorLogManager {

    /**
     * [ErrorLog]를 *ErrorLogContext&에 저장
     *
     * *ErrorLogContext*에 저장하며 *Database*에도 같이 저장함
     */
    fun save(errorLog: ErrorLog)

    /**
     * [ErrorLog]를 *ErrorLogContext*에서 불러옴
     *
     * *ErrorLogContext*에서 먼저 찾은 뒤, 존재하지 않는다면 *Database*에서 불러와 *ErrorLogContext*에 저장한 뒤 반환
     */
    fun get(id: String): ErrorLog?
}
