package com.info.maeumgagym.domain.pickle

import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.repository.PickleRepository
import com.info.maeumgagym.domain.pose.PoseTestModule
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.pickle.dto.request.UpdatePickleRequest
import java.util.*

object PickleTestModule {

    private const val PICKLE_TITLE = "테스트 피클"
    private const val PICKLE_DESCRIPTION = "테스트 피클 설명"
    private const val PICKLE_LIKE_COUNT = 0L
    private const val PICKLE_COMMENT_COUNT = 0L
    private val PICKLE_TAGS = setOf("태그1", "태그2", "태그3", PoseTestModule.createPose().simpleName)
    private val PICKLE_UPDATED_TAGS = setOf("태그1", "태그3", PoseTestModule.createPose().exactName)

    private const val UPDATE_SUFFIX = " update"

    fun createPickle(userJpaEntity: UserJpaEntity): PickleJpaEntity =
        PickleJpaEntity(
            videoId = UUID.randomUUID().toString().removeRange(8, 36),
            title = PICKLE_TITLE,
            description = PICKLE_DESCRIPTION,
            uploader = userJpaEntity,
            likeCount = PICKLE_LIKE_COUNT,
            commentCount = PICKLE_COMMENT_COUNT,
            tags = PICKLE_TAGS.toMutableSet()
        )

    fun PickleJpaEntity.saveInRepository(pickleRepository: PickleRepository): PickleJpaEntity =
        pickleRepository.save(this)
    /*
        fun getUploadPickleRequest(videoId: String): PickleUploadRequest =
            PickleUploadRequest(
                videoId = videoId,
                title = PICKLE_TITLE,
                description = PICKLE_DESCRIPTION,
                tags = PICKLE_TAGS.toMutableSet()
            )*/

    fun getUpdatePickleRequest(): UpdatePickleRequest =
        UpdatePickleRequest(
            title = PICKLE_TITLE + UPDATE_SUFFIX,
            description = PICKLE_DESCRIPTION + UPDATE_SUFFIX,
            tags = PICKLE_UPDATED_TAGS.toMutableSet()
        )

    fun verifyUpdatedPickle(pickleJpaEntity: PickleJpaEntity, uploader: UserJpaEntity): Boolean =
        getUpdatePickleRequest().run {
            pickleJpaEntity.title == title &&
                pickleJpaEntity.description == description &&
                pickleJpaEntity.tags == tags &&
                pickleJpaEntity.likeCount == 0L &&
                pickleJpaEntity.commentCount == 0L &&
                pickleJpaEntity.uploader == uploader
        }
}
