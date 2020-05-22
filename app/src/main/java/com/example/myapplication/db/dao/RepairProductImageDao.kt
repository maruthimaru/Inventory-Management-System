package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.db.table.RepairProductImage

@Dao
interface RepairProductImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<RepairProductImage>)

    @Query("Select * from repair_product_image")
    fun getAll():List<RepairProductImage>

    @Query("select * from repair_product_image where p_id = :p_id ")
    fun getpId(p_id:Int):List<RepairProductImage>

    @Query("Delete from repair_product_image where id = :p_code ")
    fun deleteSingle(p_code:Int)
}