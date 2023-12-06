package com.info.maeumgagym.domain.user.repository

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserRepository : JpaRepository<UserJpaEntity, UUID> {

    fun findByOauthId(oauthId: String): UserJpaEntity?

    @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM ${TableNames.USER_TABLE} u WHERE u.nickname = :nickname", nativeQuery = true)
    fun existsByNickname(@Param("nickname") nickname: String): Boolean

    @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM ${TableNames.USER_TABLE} u WHERE u.oauthId = :oauthId", nativeQuery = true)
    fun existsByOauthId(@Param("oauthId") oauthId: String): Boolean
}
