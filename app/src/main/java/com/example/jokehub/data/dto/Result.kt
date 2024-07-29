package com.example.jokehub.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import javax.annotation.processing.Generated

@Entity(indices = [Index(value = ["id"], unique = true)])
data class Result(

    @PrimaryKey(autoGenerate = true) val sn: Int,
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "joke") val joke: String,
    var isFav: Int = 0
)