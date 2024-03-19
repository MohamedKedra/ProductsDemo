package com.example.productsdemo.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.productsdemo.R
import com.example.productsdemo.databinding.FragmentDetailsBinding
import com.example.productsdemo.presentation.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var dataBinding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private var counter = 1

    private val viewModel by lazy {
        viewModelProvider[DetailsViewModel::class.java]
    }

    private val viewModelProvider by lazy {
        ViewModelProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = FragmentDetailsBinding.inflate(inflater)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.apply {
            args.product.apply {

                ivBack.setOnClickListener {
                    findNavController().popBackStack()
                }

                ivCart.setOnClickListener {
                    val action  = DetailsFragmentDirections.actionDetailsFragmentToCartFragment()
                    findNavController().navigate(action)
                }

                Glide.with(requireContext()).load(image).placeholder(
                    R.drawable.ic_item
                )
                    .into(ivItem)

                tvTitle.text = title
                tvOverview.text = description
                tvPrice.text = price.toString()
                tvCount.text = counter.toString()

                ivInc.setOnClickListener {
                    counter++
                    tvCount.text = counter.toString()

                }

                ivDec.setOnClickListener {
                    if (counter > 1) {
                        counter--
                        tvCount.text = counter.toString()

                    } else {
                        Toast.makeText(requireContext(), "Min count", Toast.LENGTH_SHORT).show()
                    }
                }

                viewModel.findProduct(id).observe(viewLifecycleOwner) { product ->
                    btnAddToCart.isVisible = product == null
                    btnRemoveFromCart.isVisible = product != null
                }

                btnAddToCart.setOnClickListener {
                    viewModel.addNewProduct(
                        id = id,
                        image = image.toString(),
                        title = title.toString(),
                        description = description.toString(),
                        category = category.toString(),
                        price = price!!,
                        count = counter
                    )
                    btnAddToCart.isVisible = false
                    btnRemoveFromCart.isVisible = true
                    Toast.makeText(requireContext(),"Added !!",Toast.LENGTH_SHORT).show()
                }

                btnRemoveFromCart.setOnClickListener {
                    viewModel.deleteProduct(id)
                    btnAddToCart.isVisible = true
                    btnRemoveFromCart.isVisible = false
                    Toast.makeText(requireContext(),"Deleted !!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}