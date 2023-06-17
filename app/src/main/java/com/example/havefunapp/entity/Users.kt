package com.example.havefunapp.entity

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


