package com.info.maeumgagym.auth.port.out

interface CreateUserPort {

    fun createUser(sub: String, name: String, profilePath: String?)
}
