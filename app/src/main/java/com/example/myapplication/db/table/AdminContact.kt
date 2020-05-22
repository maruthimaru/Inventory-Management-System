package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "contact")
class AdminContact {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var  id=0
    @ColumnInfo(name = "phone")
    var phone:String="";

    constructor(phone: String) {
        this.phone = phone
    }
}