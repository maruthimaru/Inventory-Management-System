package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "login")
class Login {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var  id=0
    @ColumnInfo(name = "empid")
    lateinit var empid:String;
    @ColumnInfo(name = "password")
    lateinit var password:String;
    @ColumnInfo(name = "type")
    lateinit var type:String;

    constructor(empid: String, password: String, type: String) {
        this.empid = empid
        this.password = password
        this.type = type
    }
}