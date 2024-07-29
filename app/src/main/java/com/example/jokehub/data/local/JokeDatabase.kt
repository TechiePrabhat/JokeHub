package com.example.jokehub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jokehub.data.dto.Result

@Database(entities = [Result::class], version = 1)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao

    companion object {
        @Volatile
        private var INSTANCE: JokeDatabase? = null

        fun getDatabase(contex: Context): JokeDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(contex, JokeDatabase::class.java, "jokeDB").build()
                }
            }
            return INSTANCE!!
        }
    }
}