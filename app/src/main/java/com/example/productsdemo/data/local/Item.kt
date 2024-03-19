package com.example.productsdemo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Item(
    @PrimaryKey
    val id: Int = 0,
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String,
    val count: Int
)
