package com.example.imkbstocks.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.imkbstocks.R
import com.example.imkbstocks.databinding.FragmentStockDetailBinding
import com.example.imkbstocks.databinding.FragmentStocksBinding
import com.example.imkbstocks.viewmodel.StockDetailViewModel
import com.example.imkbstocks.viewmodel.StocksViewModel

class StockDetailFragment : Fragment() {
    private lateinit var binding: FragmentStockDetailBinding
    private lateinit var viewModel: StockDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stock_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StockDetailViewModel::class.java)
        val stockId = arguments?.getInt("stockId")
        viewModel.getDetail(stockId)
        observeDetailData()
    }

    private fun observeDetailData() {

    }
}