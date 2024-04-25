package com.info.maeumgagym.application.domain.user.mapper

import com.info.maeumgagym.application.domain.user.entity.Gender
import com.info.maeumgagym.application.domain.user.entity.PhysicalInfo
import com.info.maeumgagym.application.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.core.user.model.GenderModel
import com.info.maeumgagym.core.user.model.PhysicalInfoModel
import com.info.maeumgagym.core.user.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toEntity(domain: User): UserJpaEntity =
        domain.run {
            UserJpaEntity(
                id = id,
                nickname = nickname,
                oauthId = oauthId,
                roles = roles,
                profileImage = profileImage,
                wakaStartedAt = wakaStartedAt,
                isDeletedAt = isDeletedAt,
                totalWakaTime = totalWakaTime,
                physicalInfo = physicalInfoModel?.run {
                    PhysicalInfo(
                        weight = weight,
                        height = height,
                        gender = toEntityGender(genderModel)
                    )
                }
            )
        }

    fun toDomain(entity: UserJpaEntity): User =
        entity.run {
            User(
                id = id!!,
                nickname = nickname,
                oauthId = oauthId,
                roles = roles,
                profileImage = profileImage,
                wakaStartedAt = wakaStartedAt,
                isDeletedAt = isDeletedAt,
                totalWakaTime = totalWakaTime,
                physicalInfoModel = physicalInfo?.run {
                    PhysicalInfoModel(
                        weight = weight,
                        height = height,
                        genderModel = toDomainGender(gender)
                    )
                }
            )
        }

    private fun toDomainGender(gender: Gender): GenderModel {
        return when (gender) {
            Gender.MAN -> GenderModel.MAN
            Gender.WOMAN -> GenderModel.WOMAN
            Gender.NONE -> GenderModel.NONE
        }
    }

    private fun toEntityGender(gender: GenderModel): Gender {
        return when (gender) {
            GenderModel.MAN -> Gender.MAN
            GenderModel.WOMAN -> Gender.WOMAN
            GenderModel.NONE -> Gender.NONE
        }
    }
}
