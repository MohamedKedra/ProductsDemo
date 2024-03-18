package com.example.productsdemo.data.remote.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product(
    val category: String? = null,
    val description: String? = null,
    val id: Int = 0,
    val image: String? = null,
    val price: Double? = null,
    val title: String? = null,
    val rating: Rating? = null
):Parcelable

@Parcelize
data class Rating(
    val count: Int? = null,
    val rate: Double? = null
):Parcelable