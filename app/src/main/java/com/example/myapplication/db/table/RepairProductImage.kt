package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.db.dao.ImageRegDataConversion
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "repair_product_image")
class RepairProductImage {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id=0;
    @ColumnInfo(name = "p_id")
    var pId:Int;
    @ColumnInfo(name = "p_code")
    lateinit var pCode:String;
    @ColumnInfo(name = "image")
    lateinit var image:String;

    constructor(pId: Int, pCode: String, image: String) {
        this.pId = pId
        this.pCode = pCode
        this.image = image
    }
}