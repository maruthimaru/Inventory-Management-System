package com.example.myapplication.db.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "notification")
class Notification {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    var  id=0
    @ColumnInfo(name = "message")
    lateinit var message:String;

    constructor(message: String) {
        this.message = message
    }
}