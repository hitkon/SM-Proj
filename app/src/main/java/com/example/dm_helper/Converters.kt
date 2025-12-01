package com.example.dm_helper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromIntList(value: List<Int>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String): List<Int>? {
        return Gson().fromJson(value, object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromPairList(value: List<Pair<String, Int>>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toPairList(value: String): List<Pair<String, Int>>? {
        return Gson().fromJson(value, object : TypeToken<List<Pair<String, Int>>>() {}.type)
    }
    
    @TypeConverter
    fun fromCharacterList(value: List<Character>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCharacterList(value: String): List<Character>? {
        return Gson().fromJson(value, object : TypeToken<List<Character>>() {}.type)
    }
}