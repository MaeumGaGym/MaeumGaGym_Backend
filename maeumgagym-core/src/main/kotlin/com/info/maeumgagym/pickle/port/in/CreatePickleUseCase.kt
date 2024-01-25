package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.request.CreatePickleRequest

interface CreatePickleUseCase {

    fun createPickle(req: CreatePickleRequest)
}
