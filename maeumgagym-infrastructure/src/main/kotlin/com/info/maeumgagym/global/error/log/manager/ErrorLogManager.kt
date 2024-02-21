package com.info.maeumgagym.global.error.log.manager

import com.info.maeumgagym.global.error.log.ErrorLog

interface ErrorLogManager {

    fun save(errorLog: ErrorLog)

    fun get(id: String): ErrorLog?
}
