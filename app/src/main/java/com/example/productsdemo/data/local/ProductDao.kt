package com.example.productsdemo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productsdemo.data.local.Item

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNewProductToCart(item: Item)

    @Query("SELECT * from products")
    fun getProductsCart(): LiveData<List<Item>>

    @Query("SELECT * from products where id = :id")
    fun findProduct(id: Int): LiveData<Item?>

    @Query("DELETE FROM products where id = :id")
    fun deleteProduct(id: Int)
}