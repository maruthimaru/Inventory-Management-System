package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "repair_product")
class RepairProduct {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id=0;
    @ColumnInfo(name = "p_name")
    lateinit var pName:String;
    @ColumnInfo(name = "p_code")
    lateinit var pCode:String;
    @ColumnInfo(name = "image")
    lateinit var image:String;
    @ColumnInfo(name = "dateTime")
    lateinit var dateTime:String;
    @ColumnInfo(name = "lastservicecdateTime")
    lateinit var lastservicecdateTime:String;

}