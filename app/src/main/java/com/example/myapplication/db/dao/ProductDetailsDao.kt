package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDetailsDao {

@Insert
fun insert(productDetails: ProductDetailsDao)

    @Query("Select * from product_details")
    fun getAll():List<ProductDetailsDao>
}