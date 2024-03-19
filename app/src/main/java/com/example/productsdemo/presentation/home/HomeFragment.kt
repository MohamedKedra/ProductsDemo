package com.example.productsdemo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnCloseListener
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.productsdemo.R
import com.example.productsdemo.app.base.DataState
import com.example.productsdemo.data.remote.models.Product
import com.example.productsdemo.databinding.FragmentHomeBinding
import com.example.productsdemo.presentation.home.adapter.CategoryAdapter
import com.example.productsdemo.presentation.home.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var dataBinding: FragmentHomeBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var searchView: SearchView
    private var cachedList = ArrayList<Product>()

    private val viewModel by lazy {
        viewModelProvider[HomeViewModel::class.java]
    }

    private val viewModelProvider by lazy {
        ViewModelProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentHomeBinding.inflate(inflater)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            categoryAdapter = CategoryAdapter(requireContext()) {
                viewModel.refreshProductsList(filter = it.title)
            }

            productAdapter = ProductAdapter(requireContext()) {
                val action = HomeFragmentDirections.actionListFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }

            initSearchView()
            initActions()
            initTabs()
            initList()
    }

    private fun initTabs() {
        dataBinding.apply {

            viewModel.getAllCategories()

            viewModel.categoriesListResponse.observe(viewLifecycleOwner) {

                val list = it.getData()
                categoryAdapter.submitList(list)
                rvCategories.adapter = categoryAdapter
            }
        }
    }

    private fun initActions(){
        dataBinding.apply {
            ivCart.setOnClickListener {
                val action  = HomeFragmentDirections.actionListFragmentToCartFragment()
                findNavController().navigate(action)
            }

            ivSearch.setOnClickListener {

                search.isVisible = !search.isVisible
                searchView.setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        val list = cachedList.filter {
                            it.title?.lowercase()?.contains(query.toString().lowercase()) ?: false
                        }
                        productAdapter.submitList(list)
                        rvProducts.adapter = productAdapter
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        val list = cachedList.filter {
                            it.title?.lowercase()?.contains(newText.toString().lowercase()) ?: false
                        }
                        productAdapter.submitList(list)
                        rvProducts.adapter = productAdapter
                        return false
                    }
                })

                searchView.setOnCloseListener {
                    search.isVisible = false
                    true
                }
            }

        }
    }

    private fun initSearchView(){

        searchView = SearchView(requireContext())
        searchView.setIconifiedByDefault(false)
        searchView.queryHint = getString(R.string.enter_title)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        searchView.layoutParams = layoutParams
        dataBinding.search.addView(searchView)
    }

    private fun initList() {

        with(dataBinding) {

            viewModel.refreshProductsList()

            viewModel.productsListResponse.observe(viewLifecycleOwner) { response ->
                when (response.getStatus()) {

                    DataState.DataStatus.LOADING -> {
                        showAppState()
                    }

                    DataState.DataStatus.SUCCESS -> {
                        showAppState(isLoading = false)
                        val list = response.getData()
                        cachedList = list ?: ArrayList()
                        if (list != null) {
                            productAdapter.submitList(response.getData())
                            rvProducts.adapter = productAdapter
                        } else {
                            showAppState(
                                isLoading = false,
                                isError = true,
                                errorMsg = "Empty List"
                            )
                        }
                    }

                    DataState.DataStatus.ERROR -> {
                        showAppState(
                            isLoading = false,
                            isError = true,
                            errorMsg = response.getError()?.message ?: "Something went wrong"
                        )
                    }

                    DataState.DataStatus.NO_INTERNET -> {
                        showAppState(
                            isLoading = false,
                            isError = true,
                            errorMsg = "No Internet Connection"
                        )
                    }
                }
            }
        }
    }

    private fun showAppState(
        isLoading: Boolean = true,
        isError: Boolean = false,
        errorMsg: String = ""
    ) {
        dataBinding.apply {
            shimmerLoading.apply {
                if (isLoading) startShimmer() else stopShimmer()
                isVisible = isLoading
            }

            rvProducts.isVisible = !(isLoading || isError)
            layoutEmptyOrError.isVisible = isError
            tvMessage.text = errorMsg
        }
    }
}