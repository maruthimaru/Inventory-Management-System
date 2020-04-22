package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.db.table.EmpReg
import com.example.myapplication.db.table.Login

@Dao
interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<Login>)

    @Query("select * from login")
    fun getAll():List<Login>
}