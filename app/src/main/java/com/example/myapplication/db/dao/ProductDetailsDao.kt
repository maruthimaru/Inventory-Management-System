package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.db.table.EmpReg
import com.example.myapplication.db.table.Login
import com.example.myapplication.db.table.ProductDetails

@Dao
interface ProductDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<ProductDetails>)

    @Query("Select * from product_details")
    fun getAll():List<ProductDetails>

    @Query("select * from product_details where p_code = :p_code ")
    fun getid(p_code:String):List<ProductDetails>

    @Query("UPDATE product_details SET next_service_date = :nextDate, last_service_date=:lastDate WHERE id = :uid")
    fun update(nextDate: String,lastDate: String, uid: Int?)

}