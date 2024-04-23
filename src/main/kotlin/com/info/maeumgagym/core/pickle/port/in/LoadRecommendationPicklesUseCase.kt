package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.pickle.dto.response.PickleListResponse

interface LoadRecommendationPicklesUseCase {

    fun loadRecommendationPickles(index: Int): PickleListResponse
}
