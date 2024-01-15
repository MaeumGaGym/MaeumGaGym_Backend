package com.info.maeumgagym.domain.user.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseUUIDEntity
import com.info.maeumgagym.domain.user.converter.RoleConverter
import com.info.maeumgagym.user.model.Role
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE ${TableNames.USER_TABLE} SET is_deleted = true WHERE id = ?")
@Entity(name = TableNames.USER_TABLE)
class UserJpaEntity(
    nickname: String,
    oauthId: String,
    roles: MutableList<Role>,
    profileImage: String?,
    isDelete: Boolean = false,
    wakatime: LocalTime = LocalTime.of(0, 0, 0),
    id: UUID? = null
) : BaseUUIDEntity(id) {

    @Column(name = "nickname", nullable = false, length = 10, unique = true)
    var nickname: String = nickname
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = isDelete
        protected set

    @Column(name = "oauth_id", nullable = false, length = 60, unique = true)
    val oauthId: String = oauthId

    @Convert(converter = RoleConverter::class)
    @Column(name = "roles", length = 15)
    var roles: MutableList<Role> = roles
        protected set

    @Column(name = "profile_image", nullable = true)
    var profileImage: String? = profileImage
        protected set

    @Column(name = "wakatime", nullable = false)
    var wakatime: LocalTime = wakatime
        protected set
}
