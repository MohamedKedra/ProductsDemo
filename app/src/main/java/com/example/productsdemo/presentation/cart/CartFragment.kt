package com.example.productsdemo.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.productsdemo.R
import com.example.productsdemo.databinding.FragmentCartBinding
import com.example.productsdemo.databinding.FragmentDetailsBinding
import com.example.productsdemo.presentation.details.DetailsViewModel
import com.example.productsdemo.presentation.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToLong

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var dataBinding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
    private var total = 0.0

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
                total = 0.0
                list.forEach {
                    total += it.price * it.count
                }
                tvTotalPrice.text = total.roundToLong().toString().plus(getString(R.string.currency))
                if (list.isNotEmpty()){
                    showAppState(isLoading = false)
                    adapter.submitList(list)
                    rvProducts.adapter = adapter
                }else{
                    showAppState(
                        isLoading = false,
                        isError = true,
                        errorMsg = "Empty List"
                    )
                }
            }

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnCheckout.setOnClickListener {
                Toast.makeText(requireContext(),"Say Cheese :D",Toast.LENGTH_SHORT).show()
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