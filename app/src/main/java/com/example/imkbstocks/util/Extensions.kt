package com.example.imkbstocks.util

import com.example.imkbstocks.model.ListModel

val filteredList = arrayListOf<ListModel.Stock>()

fun List<ListModel.Stock?>.getFilteredListBySymbol(symbol: String?) : List<ListModel.Stock>{
    filteredList.clear()
    for (stock in this){
        stock?.let {
            if (decrypt(it.symbol).lowercase().trim().contains(symbol.toString().lowercase().trim()))
                filteredList.add(it)
        }
    }
    return filteredList
}