package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.pickle.dto.request.UpdatePickleRequest

interface UpdatePickleUseCase {

    fun updatePickle(id: String, req: UpdatePickleRequest)
}
