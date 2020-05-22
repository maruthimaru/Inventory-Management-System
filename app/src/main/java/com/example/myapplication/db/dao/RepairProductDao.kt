package com.example.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct

@Dao
interface RepairProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tablelist: ArrayList<RepairProduct>)

    @Query("Select * from repair_product")
    fun getAll():List<RepairProduct>

    @Query("select * from repair_product where employeeid = :emp_id ")
    fun getempid(emp_id:String):List<RepairProduct>

    @Query("UPDATE repair_product SET reply_message = :reply_message WHERE id = :uid")
    fun update(reply_message: String, uid: Int?)

 @Query("select * from repair_product where reply_message!= '' and employeeid=:emp_id ")
 fun updatelist(emp_id: String):List<RepairProduct>

    @Query("Select MAX(id) from repair_product limit 1")
    fun getLastId():Int

    @Query("Delete from repair_product where id = :p_code ")
    fun deleteSingle(p_code:Int)

}