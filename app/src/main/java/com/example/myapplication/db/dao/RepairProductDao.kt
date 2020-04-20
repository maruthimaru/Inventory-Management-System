package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.db.table.RepairProduct

@Dao
interface RepairProductDao {

    @Insert
    fun insert(repairProduct: RepairProduct)

    @Query("Select * from repair_product")
    fun getAll():List<RepairProduct>
}