package com.example.havefunapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.havefunapp.entity.Users

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceUser(users: Users)

    @Query("Select * from users")
    fun gelAllUsers(): List<Users>

    @Query("SELECT COUNT(*) FROM users")
    fun countAllUsers(): Int

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUserByUsernameAndPassword(username: String, password: String): Boolean

    @Update
    fun updateUser(users: Users)

    @Delete
    fun deleteUser(users: Users)

    @Query("DELETE FROM users")
    fun deleteAllUsers()

}