package com.example.productsdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesdemo.data.local.ProductDao
import com.example.productsdemo.data.remote.models.Product
import com.example.productsdemo.data.remote.models.Rating

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}