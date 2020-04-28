package com.example.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.db.dao.EmpRegDao
import com.example.myapplication.db.dao.LoginDao
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.dao.RepairProductDao
import com.example.myapplication.db.table.*

@Database(
        entities = [Login::class,EmpReg::class,ProductDetails::class,RepairProduct::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun loginDao():LoginDao
    abstract fun productDetailDao():ProductDetailsDao
    abstract fun repairProductDao():RepairProductDao
    abstract fun empRegDao():EmpRegDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        internal fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java, "product_scanner.db"
                        )
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}
