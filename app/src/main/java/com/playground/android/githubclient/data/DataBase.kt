package com.playground.android.githubclient.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import com.playground.android.githubclient.domain.model.Repository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow

@Database(entities = [Repository::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun repositoryDao(): RepositoryDAO
}

@Dao
interface RepositoryDAO {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(repository: Repository)

  @Query("SELECT * FROM repositories WHERE topics LIKE '%' || :searchTerm || '%'")
  fun search(searchTerm: String): Flow<List<Repository>?>

  @Query("DELETE FROM repositories")
  suspend fun deleteAll()
}

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
