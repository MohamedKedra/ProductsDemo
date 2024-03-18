package com.example.productsdemo.data.remote.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class Product(
    val category: String? = null,
    val description: String? = null,
    @PrimaryKey
    val id: Int = 0,
    val image: String? = null,
    val price: Double? = null,
    val title: String? = null
)

data class Rating(
    val count: Int? = null,
    val rate: Double? = null
)