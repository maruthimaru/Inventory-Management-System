package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_details")
class ProductDetails {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var id=0;
    @ColumnInfo(name = "p_name")
    lateinit var pName:String;
    @ColumnInfo(name = "p_code")
    lateinit var pCode:String;
    @ColumnInfo(name = "image")
    lateinit var image:String;
    @ColumnInfo(name = "date")
    lateinit var date:String;
    @ColumnInfo(name = "time")
    lateinit var time:String;
    @ColumnInfo(name = "next_service_date")
    lateinit var nextServiceDate:String;
    @ColumnInfo(name = "last_service_date")
    lateinit var lastServiceDate:String;

    constructor(pName: String, pCode: String, image: String, date: String, time: String, nextServiceDate: String, lastServiceDate: String) {
        this.pName = pName
        this.pCode = pCode
        this.image = image
        this.date = date
        this.time = time
        this.nextServiceDate = nextServiceDate
        this.lastServiceDate = lastServiceDate
    }
}




