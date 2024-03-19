package com.example.productsdemo.presentation.details

import com.example.productsdemo.app.base.BaseViewModel
import com.example.productsdemo.app.utils.ConnectionManager
import com.example.productsdemo.data.local.Item
import com.example.productsdemo.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    fun addNewProduct(
        id: Int,
        image: String,
        title: String,
        price: Double,
        count: Int,
        category: String,
        description: String
    ) {
        val item = Item(
            id, category, description, image, price, title, count
        )

        repository.addNewProductToCart(item)
    }

    fun findProduct(id:Int) = repository.findProduct(id)

    fun deleteProduct(id:Int) = repository.deleteProduct(id)
}