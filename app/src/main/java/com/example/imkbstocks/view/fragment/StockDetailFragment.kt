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
import com.example.imkbstocks.util.MyMarkerView
import com.example.imkbstocks.util.renderData
import com.example.imkbstocks.viewmodel.StockDetailViewModel

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
        configureChart()
        viewModel.getDetailData(stockId)
        observeDetailData()
    }

    private fun configureChart() {
        binding.stockDetailChart.setTouchEnabled(true)
        binding.stockDetailChart.setPinchZoom(false)
        val mv = MyMarkerView(requireContext(), R.layout.custom_marker_view)
        mv.chartView = binding.stockDetailChart
        binding.stockDetailChart.marker = mv
    }

    private fun observeDetailData() {
        viewModel.detailResponseStatus.observe(viewLifecycleOwner , { detailResponseStatus ->

        })

        viewModel.detail.observe(viewLifecycleOwner , { detailData ->
            binding.detail = detailData
            binding.stockDetailChart.renderData(detailData.graphicData)
        })
    }


}