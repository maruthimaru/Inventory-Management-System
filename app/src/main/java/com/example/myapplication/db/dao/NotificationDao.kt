package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.db.table.EmpReg
import com.example.myapplication.db.table.Notification

@Dao
interface NotificationDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: Notification)

    @Query("select * from notification")
    fun getAll():List<Notification>

    @Query("Delete from notification")
    fun deleteAll()

}