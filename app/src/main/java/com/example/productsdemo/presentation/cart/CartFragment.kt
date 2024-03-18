package com.example.productsdemo.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.productsdemo.R
import com.example.productsdemo.databinding.FragmentCartBinding
import com.example.productsdemo.databinding.FragmentDetailsBinding
import com.example.productsdemo.presentation.details.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var dataBinding: FragmentCartBinding
    private lateinit var adapter: CartAdapter

    private val viewModel by lazy {
        viewModelProvider[CartViewModel::class.java]
    }

    private val viewModelProvider by lazy {
        ViewModelProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentCartBinding.inflate(inflater)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            adapter = CartAdapter(requireContext()){
                viewModel.deleteProduct(it.id)
            }

            rvProducts.adapter = adapter

            viewModel.getProductsCart().observe(viewLifecycleOwner){ list ->
                adapter.submitList(list)
                rvProducts.adapter = adapter
            }

            tvTotalPrice.text = viewModel.calculateTotalPrice().toString().plus(getString(R.string.currency))
        }
    }
}