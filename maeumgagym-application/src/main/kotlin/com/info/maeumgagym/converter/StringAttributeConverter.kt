package com.info.maeumgagym.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
internal class StringAttributeConverter : AttributeConverter<MutableSet<String>, String> {

    private companion object {
        const val SPLIT_CHAR = ","
    }

    override fun convertToDatabaseColumn(attribute: MutableSet<String>): String {
        return attribute.joinToString(SPLIT_CHAR)
    }

    override fun convertToEntityAttribute(dbData: String): MutableSet<String> =
        dbData.split(SPLIT_CHAR).toMutableSet()
}
