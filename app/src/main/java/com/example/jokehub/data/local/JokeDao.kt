package com.example.jokehub.data.local

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jokehub.data.dto.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Query("SELECT * FROM Result")
    fun getAllJoke():List<Result>

    @Insert
    fun insertAllJoke(jokes:List<Result>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJoke(joke:Result)

  /*  @Query("SELECT * FROM Result ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getJokes(limit:Int,offset:Int):PagingSource<Int,Result>*/

    @Query("SELECT * FROM Result ORDER BY id ASC")
    fun getJokes():PagingSource<Int,Result>

    @Query("SELECT * FROM Result ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getFabJokes(limit: Int,offset: Int):List<Result>

    @Query("DELETE From Result WHERE id=:id")
    fun deleteJokeID(id:String)
}