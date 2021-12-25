package com.example.imkbstocks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imkbstocks.R
import com.example.imkbstocks.databinding.StockListItemBinding
import com.example.imkbstocks.generated.callback.OnClickListener
import com.example.imkbstocks.model.ListModel
import com.example.imkbstocks.util.decrypt
import com.example.imkbstocks.util.format
import kotlin.math.abs

class StocksAdapter : RecyclerView.Adapter<StocksAdapter.StockViewHolder>() {
    private var onStockClickListener: OnStockClickListener? = null
    private val stockList = arrayListOf<ListModel.Stock?>()

    class StockViewHolder(var binding: StockListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StockViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.stock_list_item, parent, false))

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        configureUI(holder, position)
    }

    private fun configureUI(holder: StockViewHolder, position: Int) {
        holder.binding.stock = stockList[position]
        holder.binding.position = position
        onStockClickListener?.let { holder.binding.listener = it  }
    }

    override fun getItemCount() = stockList.size

    internal fun updateStockList(newStockList: List<ListModel.Stock?>?){
        newStockList?.let {
            stockList.clear()
            stockList.addAll(it)
            notifyDataSetChanged()
        }
    }

    internal fun setOnStockClickListener(onStockClickListener: OnStockClickListener){
        this.onStockClickListener = onStockClickListener
    }

    interface OnStockClickListener {
        fun onStockClicked(view: View, stockId : Int)
    }

}