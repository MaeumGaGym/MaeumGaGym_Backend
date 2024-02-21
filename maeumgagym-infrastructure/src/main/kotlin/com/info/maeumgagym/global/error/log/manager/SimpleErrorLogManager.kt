package com.info.maeumgagym.global.error.log.manager

import com.info.maeumgagym.global.error.log.ErrorLog
import org.springframework.stereotype.Component

@Component
class SimpleErrorLogManager : ErrorLogManager {

    private companion object {
        val errorLogContext: MutableMap<String, ErrorLog> = mutableMapOf()
    }

    override fun save(errorLog: ErrorLog) {
        errorLogContext[errorLog.id] = errorLog
    }

    override fun get(id: String): ErrorLog? = errorLogContext[id]
}
