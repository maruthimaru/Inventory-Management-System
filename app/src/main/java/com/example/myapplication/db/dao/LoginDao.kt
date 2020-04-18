package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoginDao {

    @Insert
    fun insert(login: LoginDao)

    @Query("select * from login")
    fun getAll():List<LoginDao>
}