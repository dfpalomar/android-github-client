package com.playground.android.githubclient.data

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converter {

  private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

  private val membersType = Types.newParameterizedType(List::class.java, String::class.java)
  private val membersAdapter = moshi.adapter<List<String>>(membersType)

  @TypeConverter
  fun jsonToList(string: String): List<String> {
    return membersAdapter.fromJson(string).orEmpty()
  }

  @TypeConverter
  fun listToJson(members: List<String>): String {
    return membersAdapter.toJson(members)
  }
}
