package com.example.havefunapp.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users(
    @PrimaryKey(
        autoGenerate = true
    )
    val userId: Int? = null,
    val userName: String,
    val password: String,
    val idUSer:String,
    val email:String
)


