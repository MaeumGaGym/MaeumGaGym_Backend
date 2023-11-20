package com.info.maeumgagym.domain.user.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseUUIDEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.Column
import javax.persistence.Entity

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE ${TableNames.USER_TABLE} SET is_deleted = true WHERE id = ?")
@Entity(name = TableNames.USER_TABLE)
class User(
    name: String,
    oauthId: String
) : BaseUUIDEntity() {

    @Column(name = "nickname", nullable = false, length = 10, unique = true)
    var nickname: String = name
        protected set

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false
        protected set

    fun deleteUser() {
        this.isDeleted = true
    }

    fun restoreUser() {
        this.isDeleted = false
    }

    @Column(name = "oauth_id", nullable = false, length = 60, unique = true)
    val oauthId: String = oauthId
}