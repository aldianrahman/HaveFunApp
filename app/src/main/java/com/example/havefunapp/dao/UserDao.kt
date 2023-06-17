package com.example.havefunapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.havefunapp.entity.Users

@Dao
interface UserDao {

    @Query("SELECT idUSer FROM users WHERE email = :email")
    fun getUserIdByEmail(email: String): String

    @Query("SELECT userName FROM users WHERE email = :email")
    fun getUsernameByEmail(email: String): String

    @Query("INSERT OR REPLACE INTO Users (idUser, userName, password,email) VALUES (:idUser, :userName, :password, :email)")
    fun insertOrReplaceUser(idUser:String,userName:String,password: String,email: String)

    @Query("Select * from users")
    fun gelAllUsers(): List<Users>

    @Query("SELECT COUNT(*) FROM users")
    fun countAllUsers(): Int

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getUserByUsernameAndPassword(email: String, password: String): Boolean

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): Boolean



    @Update
    fun updateUser(users: Users)

    @Delete
    fun deleteUser(users: Users)

    @Query("DELETE FROM users")
    fun deleteAllUsers()

}