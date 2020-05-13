package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


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


    constructor(
        pName: String,
        pCode: String,
        image: String,
        date: String,
        time: String,
        lastservicecdateTime: String,
        employeeid: String,
        reply_message: String
    ) {
        this.pName = pName
        this.pCode = pCode
        this.image = image
        this.date = date
        this.time = time
        this.lastservicecdateTime = lastservicecdateTime
        this.employeeid=employeeid
        this.reply_message=reply_message
    }
}