package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.request.UpdatePickleRequest

interface UpdatePickleUseCase {

    fun updatePickle(id: String, req: UpdatePickleRequest)
}
