package com.thewire.wenlaunch.cache.model.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LaunchTypeConverter {
    @TypeConverter
    fun listToString(stringList: List<String>): String {
        return Gson().toJson(stringList)
    }

    @TypeConverter
    fun stringToList(string: String): List<String> {
        val stringType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, stringType)
    }
}