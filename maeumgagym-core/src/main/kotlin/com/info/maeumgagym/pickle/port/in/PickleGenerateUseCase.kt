package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.request.PicklePostRequest

interface PickleGenerateUseCase {

    fun execute(req: PicklePostRequest)
}
