package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.db.table.Login

@Dao
interface LoginDao {

    @Insert
    fun insert(login: Login)

    @Query("select * from login")
    fun getAll():List<Login>
}