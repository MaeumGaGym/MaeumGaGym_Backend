package com.info.maeumgagym.infrastructure.error.resolver

import com.info.maeumgagym.infrastructure.error.vo.ErrorInfo

interface ErrorResolver {

    fun resolve(errorInfo: ErrorInfo)
}
