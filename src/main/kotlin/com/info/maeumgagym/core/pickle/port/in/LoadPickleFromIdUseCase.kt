package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.pickle.dto.response.PickleResponse

interface LoadPickleFromIdUseCase {

    fun loadPickleFromId(id: String): PickleResponse
}
