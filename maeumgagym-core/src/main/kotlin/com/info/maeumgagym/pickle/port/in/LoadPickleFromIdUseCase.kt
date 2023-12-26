package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PickleResponse

interface LoadPickleFromIdUseCase {

    fun loadPickleFromId(id: Long): PickleResponse
}
