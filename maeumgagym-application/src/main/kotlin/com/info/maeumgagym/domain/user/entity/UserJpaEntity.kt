package com.info.maeumgagym.domain.user.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseUUIDEntity
import com.info.maeumgagym.domain.user.converter.RoleConverter
import com.info.maeumgagym.user.model.Role
import org.hibernate.annotations.SQLDelete
import java.util.*
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity

@SQLDelete(sql = "UPDATE ${TableNames.USER_TABLE} SET is_deleted = true WHERE id = ?")
@Entity(name = TableNames.USER_TABLE)
class UserJpaEntity(
    id: UUID?,
    nickname: String,
    oauthId: String,
    roles: MutableList<Role>,
    profilePath: String?,
    isDelete: Boolean
) : BaseUUIDEntity(id) {

    @Column(name = "nickname", nullable = false, length = 10, unique = true)
    var nickname: String = nickname
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    @Column(name = "oauth_id", nullable = false, length = 60, unique = true)
    val oauthId: String = oauthId

    @Convert(converter = RoleConverter::class)
    @Column(name = "roles", length = 15)
    var roles: MutableList<Role> = roles
        protected set

    @Column(name = "profile_path", nullable = true)
    var profilePath: String? = profilePath
        protected set

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun restoreUser() {
        this.isDeleted = false
    }

    fun updateProfile(profilePath: String) {
        this.profilePath = profilePath
    }
}
