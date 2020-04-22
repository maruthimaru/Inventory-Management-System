package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empreg")
class EmpReg {

    @PrimaryKey(autoGenerate = true)
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
    @ColumnInfo(name = "date")
    lateinit var date:String;
    @ColumnInfo(name ="time")
    lateinit var time:String;
    constructor(
        name: String,
        age: String,
        phNo: String,
        empId: String,
        password: String,
        type: String,
        date: String,
        time: String
    ) {
        this.name = name
        this.age = age
        this.phNo = phNo
        this.empId = empId
        this.password = password
        this.type = type
        this.date = date
        this.time = time
    }
}
