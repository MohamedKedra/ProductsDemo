package com.example.productsdemo.data.repository

import com.example.productsdemo.data.local.Item
import com.example.productsdemo.data.local.ProductDao
import com.example.productsdemo.data.remote.ProductService
import java.util.ArrayList
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val service: ProductService,
    private val dao: ProductDao
) {

    suspend fun getAllProducts() = service.getAllProducts()
    suspend fun getProductsByCategory(filter:String) = service.getProductsByCategory(filter)
    suspend fun getAllCategories() = service.getAllCategories()

    fun addNewProductToCart(item: Item) = dao.addNewProductToCart(item)
    fun getProductsCart() = dao.getProductsCart()
    fun findProduct(id:Int) = dao.findProduct(id)
    fun deleteProduct(id:Int) = dao.deleteProduct(id)
}