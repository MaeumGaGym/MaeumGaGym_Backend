package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.dto.response.PickleListResponse
import com.info.maeumgagym.pickle.dto.response.PickleResponse
import com.info.maeumgagym.pickle.exception.ThereNoPicklesException
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.port.`in`.LoadRecommendationPicklesUseCase
import com.info.maeumgagym.pickle.port.out.ReadAllPicklesPort
import com.info.maeumgagym.user.dto.response.UserResponse
import com.info.maeumgagym.user.model.User

@UseCase
class LoadPickleService(
    private val readAllPicklesPort: ReadAllPicklesPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : LoadRecommendationPicklesUseCase {

    private companion object {
        const val INDEX_SIZE = 5
    }

    override fun loadRecommendationPickles(index: Int): PickleListResponse {

        val allPickles = readAllPicklesPort.readAllPickles()

        if (allPickles.isEmpty() || allPickles.size <= index * INDEX_SIZE) throw ThereNoPicklesException

        val pickles: List<Pickle> = if (allPickles.size < INDEX_SIZE) allPickles else getRandomPickles(allPickles)

        return PickleListResponse(
            readCurrentUserPort.readCurrentUser().run {
                pickles.map { it.toResponseWithUser(this) }
            }
        )
    }

    private fun getRandomPickles(pickles: List<Pickle>): List<Pickle> =
        mutableSetOf<Pickle>().apply {
            while (size < INDEX_SIZE) {
                add(pickles.random())
            }
        }.toList()

    private fun Pickle.toResponseWithUser(user: User): PickleResponse =
        PickleResponse(
            id = id!!,
            videoUrl = videoUrl,
            title = title,
            description = description,
            tags = tags.toList(),
            likeCount = likeCount,
            commentCount = commentCount,
            userInfo = UserResponse(
                nickname = user.nickname,
                profileImage = user.profileImage
            )
        )
}
