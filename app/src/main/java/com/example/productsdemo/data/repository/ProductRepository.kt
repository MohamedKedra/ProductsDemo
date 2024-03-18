package com.example.productsdemo.data.repository

import com.example.moviesdemo.data.local.ProductDao
import com.example.productsdemo.data.remote.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val service: ProductService,
    private val dao: ProductDao
) {

    suspend fun getAllProducts() = service.getAllProducts()
    suspend fun getProductsByCategory(filter:String) = service.getProductsByCategory(filter)
    suspend fun getAllCategories() = service.getAllCategories()

}