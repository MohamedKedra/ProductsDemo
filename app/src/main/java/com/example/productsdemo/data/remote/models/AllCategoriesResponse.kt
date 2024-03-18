package com.example.productsdemo.data.remote.models

class AllCategoriesResponse : ArrayList<String>()

data class Category(
    var title: String,
    var isSelected: Boolean = false,
)