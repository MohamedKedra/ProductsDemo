package com.example.productsdemo.data.remote

import com.example.productsdemo.data.remote.models.AllCategoriesResponse
import com.example.productsdemo.data.remote.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products")
    suspend fun getAllProducts(): Response<ArrayList<Product>>

    @GET("products/categories")
    suspend fun getAllCategories(): Response<AllCategoriesResponse>

    @GET("products/category/{filter}")
    suspend fun getProductsByCategory(@Path("filter") filter: String): Response<ArrayList<Product>>
}