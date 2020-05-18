package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.db.table.RepairProduct

@Dao
interface RepairProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<RepairProduct>)

    @Query("Select * from repair_product")
    fun getAll():List<RepairProduct>

    @Query("UPDATE repair_product SET reply_message = :reply_message WHERE id = :uid")
    fun update(reply_message: String, uid: Int?)
}