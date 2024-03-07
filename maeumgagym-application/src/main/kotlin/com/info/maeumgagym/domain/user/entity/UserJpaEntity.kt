package com.info.maeumgagym.domain.user.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseUUIDEntity
import com.info.maeumgagym.domain.user.converter.RoleConverter
import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import com.info.maeumgagym.user.model.Role
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted_at IS NULL")
@SQLDelete(
    sql = "UPDATE ${TableNames.USER_TABLE} SET is_deleted_at = CURRENT_TIME, waka_started_at = null WHERE id = ?"
)
@Entity(name = TableNames.USER_TABLE)
class UserJpaEntity(
    nickname: String,
    oauthId: String,
    roles: MutableList<Role>,
    profileImage: String?,
    wakaStartedAt: LocalDateTime? = null,
    isDeletedAt: LocalDateTime? = null,
    id: UUID? = null,
    totalWakaTime: Long = 0,
    physicalInfo: PhysicalInfo?
) : BaseUUIDEntity(id) {

    @Column(name = "nickname", nullable = false, length = 10, unique = true)
    var nickname: String = nickname
        protected set

    @Column(name = "is_deleted_at", nullable = true)
    var isDeletedAt: LocalDateTime? = isDeletedAt
        protected set

    @Column(name = "oauth_id", nullable = false, length = 60, unique = true)
    val oauthId: String = oauthId

    @Convert(converter = RoleConverter::class)
    @Column(name = "roles", length = 15, nullable = false)
    var roles: MutableList<Role> = roles
        protected set

    @Column(name = "profile_image")
    var profileImage: String? = profileImage
        protected set

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var wakaTime: MutableList<WakaTimeJpaEntity> = arrayListOf()
        protected set

    @Column(name = "waka_started_at")
    var wakaStartedAt: LocalDateTime? = wakaStartedAt
        protected set

    @Column(name = "total_wakatime", columnDefinition = "BIGINT default 0", nullable = false)
    var totalWakaTime: Long = totalWakaTime
        protected set

    @Embedded
    var physicalInfo: PhysicalInfo? = physicalInfo
        protected set
}
