package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PickleListResponse

interface LoadRecommendationPicklesUseCase {

    fun loadRecommendationPickles(index: Int): PickleListResponse
}
