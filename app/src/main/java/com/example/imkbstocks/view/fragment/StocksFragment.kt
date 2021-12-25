package com.example.imkbstocks.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imkbstocks.Periods
import com.example.imkbstocks.R
import com.example.imkbstocks.StockListStatus
import com.example.imkbstocks.adapter.StocksAdapter
import com.example.imkbstocks.databinding.FragmentStocksBinding
import com.example.imkbstocks.util.showErrorMessage
import com.example.imkbstocks.viewmodel.StocksViewModel

class StocksFragment : Fragment() {
    private lateinit var binding: FragmentStocksBinding
    private lateinit var viewModel: StocksViewModel
    private val stocksAdapter = StocksAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stocks, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StocksViewModel::class.java)
        configureUI()
        configListeners()
        viewModel.getStockList(Periods.ALL.period)
        observeLiveData()
    }

    private fun configureUI() {
        val toggle = ActionBarDrawerToggle(requireActivity(), binding.filterDrawer, binding.toolbarStocks, 0,0)
        binding.filterDrawer.addDrawerListener(toggle)
        toggle.syncState()
        binding.rvStockList.layoutManager = LinearLayoutManager(context)
        binding.rvStockList.adapter = stocksAdapter
    }

    private fun observeLiveData() {
        viewModel.stockListStatus.observe(viewLifecycleOwner, { stockListStatus ->
            binding.pbStockList.visibility = stockListStatus.pbStockListVisibility
            binding.rvStockList.visibility = stockListStatus.rvStockListVisibility
            if (stockListStatus == StockListStatus.FAILURE) showErrorMessage(context)
        })
        viewModel.stockList.observe(viewLifecycleOwner, { stockList ->
            stocksAdapter.updateStockList(stockList)
        })
    }

    private fun configListeners() {
        stocksAdapter.setOnStockClickListener(object : StocksAdapter.OnStockClickListener{
            override fun onStockClicked(view: View, stockId: Int) {
                goToStockDetailFragment(view, stockId)
            }
        })

        binding.nvFilter.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_stock -> viewModel.getStockList(Periods.ALL.period)
                R.id.item_up -> viewModel.getStockList(Periods.INCREASING.period)
                R.id.item_down -> viewModel.getStockList(Periods.DECREASING.period)
                R.id.item_vlm_30 -> viewModel.getStockList(Periods.VOLUME_30.period)
                R.id.item_vlm_50 -> viewModel.getStockList(Periods.VOLUME_50.period)
                R.id.item_vlm_100 -> viewModel.getStockList(Periods.VOLUME_100.period)
            }
            binding.filterDrawer.closeDrawer(GravityCompat.START)
            true
        }

        binding.filterDrawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                if (slideOffset != 0f && slideOffset > 0.35f) {
                    requireActivity().window.statusBarColor = resources.getColor(R.color.gray)
                    binding.searchStocks.clearFocus()
                }
                else requireActivity().window.statusBarColor = resources.getColor(R.color.red)
            }
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    private fun goToStockDetailFragment(view: View, stockId: Int) {
        val action = StocksFragmentDirections.actionStocksFragmentToStockDetailFragment(stockId)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.filterDrawer.isDrawerOpen(GravityCompat.START)) {
                    binding.filterDrawer.closeDrawer(GravityCompat.START)
                }else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callBack)
    }

}