package com.example.productsdemo.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.productsdemo.app.base.BaseViewModel
import com.example.productsdemo.app.base.LiveDataState
import com.example.productsdemo.app.utils.ConnectionManager
import com.example.productsdemo.data.remote.models.Category
import com.example.productsdemo.data.remote.models.Product
import com.example.productsdemo.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val connectionManager: ConnectionManager
) : BaseViewModel() {

    val productsListResponse = LiveDataState<ArrayList<Product>>()
    val categoriesListResponse = LiveDataState<ArrayList<Category>>()

    fun refreshProductsList(filter: String = "All") {

        publishLoading(productsListResponse)

        if (!connectionManager.isNetworkAvailable) {
            publishNoInternet(productsListResponse)
            return
        }

        viewModelScope.launch {
            val result =
                if (filter == "All") repository.getAllProducts() else
                    repository.getProductsByCategory(filter)

            if (result.isSuccessful) {
                delay(1000)
                println("output api at ${System.currentTimeMillis()}")
                publishResult(productsListResponse, result.body())
            } else {
                publishError(productsListResponse, result.message())
            }
        }
    }

    fun getAllCategories() {

        publishLoading(categoriesListResponse)

        if (!connectionManager.isNetworkAvailable) {
            publishNoInternet(categoriesListResponse)
            return
        }

        viewModelScope.launch {
            val result = repository.getAllCategories()

            if (result.isSuccessful) {
                delay(1000)
                val list = ArrayList<Category>()
                list.add(0, Category(title = "All", isSelected = true))
                result.body()?.forEach { item -> list.add(Category(title = item)) }
                publishResult(categoriesListResponse, list)
            } else {
                publishError(categoriesListResponse, result.message())
            }
        }
    }
}