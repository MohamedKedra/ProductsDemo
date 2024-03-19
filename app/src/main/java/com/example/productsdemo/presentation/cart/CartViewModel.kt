package com.example.productsdemo.presentation.cart

import com.example.productsdemo.app.base.BaseViewModel
import com.example.productsdemo.app.utils.ConnectionManager
import com.example.productsdemo.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    fun getProductsCart() = repository.getProductsCart()

    fun deleteProduct(id: Int) = repository.deleteProduct(id)

    fun calculateTotalPrice(): Double {
        var total = 0.0
        val items = repository.getProductsCart().value
        items?.forEach {
            total += it.price
        }
        return total
    }

}