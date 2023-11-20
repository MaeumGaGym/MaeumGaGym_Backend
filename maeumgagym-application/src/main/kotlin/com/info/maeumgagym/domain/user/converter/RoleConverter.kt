package com.info.maeumgagym.domain.user.converter

import com.info.maeumgagym.domain.user.exception.RoleConverterException
import com.info.maeumgagym.domain.user.role.Role
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class RoleConverter : AttributeConverter<MutableList<Role>, String> {

    private companion object {
        const val SPLIT_CHAR = ","
    }

    override fun convertToDatabaseColumn(attribute: MutableList<Role>): String =
        String.format(SPLIT_CHAR, attribute)

    override fun convertToEntityAttribute(dbData: String): MutableList<Role> =
        dbData.split(SPLIT_CHAR).map {
            when (it) {
                Role.USER.name -> Role.USER
                Role.ADMIN.name -> Role.ADMIN
                Role.SELLER.name -> Role.SELLER
                else -> throw RoleConverterException
            }
        }.toMutableList()
}