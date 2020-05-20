package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.db.dao.ImageRegDataConversion
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "repair_product")
class RepairProduct {

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
    @ColumnInfo(name = "lastservicecdateTime")
    lateinit var lastservicecdateTime:String;
    @ColumnInfo(name = "employeeid")
    lateinit var employeeid:String;
    @ColumnInfo(name = "reply_message")
    lateinit var reply_message:String;
    @TypeConverters(ImageRegDataConversion::class)
    @SerializedName("imagelist")
    @Expose
    var imagelist : List<String>?=null


    constructor(
        pName: String,
        pCode: String,
        image: String,
        date: String,
        time: String,
        lastservicecdateTime: String,
        employeeid: String,
        reply_message: String,
        imagelist: List<String>
    ) {
        this.pName = pName
        this.pCode = pCode
        this.image = image
        this.date = date
        this.time = time
        this.lastservicecdateTime = lastservicecdateTime
        this.employeeid=employeeid
        this.reply_message=reply_message
        this.imagelist = imagelist
    }
}