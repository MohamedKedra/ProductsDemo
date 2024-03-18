package com.example.productsdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesdemo.data.local.ProductDao

//@Database(entities = [Demo::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}