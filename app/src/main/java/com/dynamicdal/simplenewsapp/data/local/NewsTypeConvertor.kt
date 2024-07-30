package com.dynamicdal.simplenewsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dynamicdal.simplenewsapp.data.local.enitites.SourceEntity

@ProvidedTypeConverter
class NewsTypeConvertor {

    @TypeConverter
    fun sourceToString(source: SourceEntity): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): SourceEntity {
        return source.split(",").let { sourceArray ->
            SourceEntity(sourceArray[0], sourceArray[1])
        }
    }

}