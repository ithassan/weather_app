package com.example.myweatherforcast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "setting_tbl")
data class Units(
    @Nonnull
    @PrimaryKey
    @ColumnInfo(name = "unit")
    val unit : String
)
