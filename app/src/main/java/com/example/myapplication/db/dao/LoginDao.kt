package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.db.table.Login
import org.intellij.lang.annotations.Flow

@Dao
interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<Login>)

    @Query("select * from login")
    fun getAll():List<Login>
//
//    @Query("select * from login where empid = :empid")
//  fun getemp_id(empid:String):List<Login>

    @Query("select empid from login")
    fun getemp_id():String
}