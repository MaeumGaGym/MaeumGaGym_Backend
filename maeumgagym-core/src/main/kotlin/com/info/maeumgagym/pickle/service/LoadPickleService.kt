package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.pickle.dto.response.PickleListResponse
import com.info.maeumgagym.pickle.dto.response.PickleResponse
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.exception.ThereNoPicklesException
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.port.`in`.LoadPickleFromIdUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadRecommendationPicklesUseCase
import com.info.maeumgagym.pickle.port.out.GenerateUploadURLPort
import com.info.maeumgagym.pickle.port.out.ReadAllPicklesPort
import com.info.maeumgagym.pickle.port.out.ReadPickleByIdPort
import com.info.maeumgagym.user.dto.response.UserResponse

@UseCase
class LoadPickleService(
    private val readAllPicklesPort: ReadAllPicklesPort,
    private val readPickleByIdPort: ReadPickleByIdPort,
    private val generateUploadURLPort: GenerateUploadURLPort
) : LoadRecommendationPicklesUseCase, LoadPickleFromIdUseCase {

    private companion object {
        const val INDEX_SIZE = 5
    }

    override fun loadRecommendationPickles(index: Int): PickleListResponse {
        val allPickles = readAllPicklesPort.readAllPickles()

        if (allPickles.isEmpty() || allPickles.size <= index * INDEX_SIZE) throw ThereNoPicklesException

        val pickles: List<Pickle> = if (allPickles.size < INDEX_SIZE) allPickles else getRandomPickles(allPickles)

        return PickleListResponse(
            pickles.map { it.toResponse() }
        )
    }

    override fun loadPickleFromId(id: Long): PickleResponse =
        (readPickleByIdPort.readPickleById(id) ?: throw PickleNotFoundException).toResponse()

    private fun getRandomPickles(pickles: List<Pickle>): List<Pickle> =
        mutableSetOf<Pickle>().apply {
            while (size < INDEX_SIZE) {
                add(pickles.random())
            }
        }.toList()

    private fun Pickle.toResponse(): PickleResponse =
        PickleResponse(
            videoId = videoId,
            videoURL = generateUploadURLPort.generateUploadURL(videoId),
            title = title,
            description = description,
            tags = tags.toList(),
            likeCount = likeCount,
            commentCount = commentCount,
            userInfo = UserResponse(
                nickname = uploader.nickname,
                profileImage = uploader.profileImage
            )
        )
}
