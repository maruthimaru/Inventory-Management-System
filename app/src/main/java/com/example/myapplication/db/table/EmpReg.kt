package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empreg")
class EmpReg {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id=0;
    @ColumnInfo(name = "name")
    lateinit var name:String;
    @ColumnInfo(name = "age")
    lateinit var age:String;
    @ColumnInfo(name = "phno")
    lateinit var phNo:String;
    @ColumnInfo(name = "empid")
    lateinit var empId:String;
    @ColumnInfo(name = "password")
    lateinit var password:String;
    @ColumnInfo(name = "type")
    lateinit var type:String;
    @ColumnInfo(name = "dateTime")
    lateinit var dateTime:String;
}