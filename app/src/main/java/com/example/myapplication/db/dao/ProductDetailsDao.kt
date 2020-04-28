package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.db.table.EmpReg
import com.example.myapplication.db.table.ProductDetails

@Dao
interface ProductDetailsDao {

@Insert
fun insert(productDetails: ProductDetails)

    @Query("Select * from product_details")
    fun getAll():List<ProductDetails>

    @Query("select * from product_details where p_code = :p_code ")
    fun getid(p_code:String):List<ProductDetails>

}