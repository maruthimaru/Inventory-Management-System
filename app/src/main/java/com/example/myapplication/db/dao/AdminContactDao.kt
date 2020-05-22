package com.example.myapplication.db.dao

import androidx.room.*
import com.example.myapplication.db.table.AdminContact
import com.example.myapplication.db.table.EmpReg

@Dao
interface AdminContactDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<AdminContact>)

    @Query("select * from contact")
    fun getAll():List<AdminContact>

    @Query("select phone from contact")
    fun getPhone():String

    @Query("Update contact set phone=:phn where id=:id")
    fun update(phn:String, id:Int)



}