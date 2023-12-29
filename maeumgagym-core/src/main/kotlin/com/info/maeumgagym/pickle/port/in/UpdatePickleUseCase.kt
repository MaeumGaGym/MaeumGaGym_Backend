package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.request.UpdatePickleRequest
import com.info.maeumgagym.pickle.model.Pickle

interface UpdatePickleUseCase {

    fun updatePickle(id: String, req: UpdatePickleRequest): Pickle
}
