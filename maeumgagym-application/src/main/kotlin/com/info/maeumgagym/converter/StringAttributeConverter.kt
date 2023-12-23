package com.info.maeumgagym.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class StringAttributeConverter : AttributeConverter<MutableList<String>, String> {

    private companion object {
        const val SPLIT_CHAR = ","
    }

    override fun convertToDatabaseColumn(attribute: MutableList<String>): String {
        return attribute.joinToString(SPLIT_CHAR)
    }

    override fun convertToEntityAttribute(dbData: String): MutableList<String> =
        dbData.split(SPLIT_CHAR).toMutableList()
}
