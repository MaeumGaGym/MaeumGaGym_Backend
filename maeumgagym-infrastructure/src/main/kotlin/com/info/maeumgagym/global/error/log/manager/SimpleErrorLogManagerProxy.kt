package com.info.maeumgagym.global.error.log.manager

import com.info.maeumgagym.error.ErrorLogJpaEntity
import com.info.maeumgagym.error.ErrorLogRepository
import com.info.maeumgagym.global.error.log.ErrorLog
import com.info.maeumgagym.global.error.log.ErrorLogLayer
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class SimpleErrorLogManagerProxy(
    private val simpleErrorLogManager: SimpleErrorLogManager,
    private val errorLogRepository: ErrorLogRepository
) : ErrorLogManager {

    override fun save(errorLog: ErrorLog) {
        errorLogRepository.save(errorLog.run {
            ErrorLogJpaEntity(
                status = status,
                message = message,
                log = log,
                layer = layer.value,
                timestamp = timestamp,
                id = id
            )
        })
        simpleErrorLogManager.save(errorLog)
    }

    override fun get(id: String): ErrorLog? =
        simpleErrorLogManager.get(id) ?: errorLogRepository.findById(id)?.run {
            ErrorLog(
                status = status,
                message = message,
                log = log,
                layer = ErrorLogLayer.of(layer),
                timestamp = timestamp,
                id = id
            )
        }
}
