package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.db.table.EmpReg

@Dao
interface EmpRegDao{

    @Insert
    fun insert(empReg: EmpReg)

    @Query("select * from empreg")
    fun getAll():List<EmpReg>
}