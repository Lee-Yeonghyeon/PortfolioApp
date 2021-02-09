package com.example.portfolioapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PrizeListAdapter(val context: Context, val prizeList: ArrayList<ItemPrize>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_homeprize, null)

        val h_prize_title =  view.findViewById<TextView>(R.id.h_prize_title)
        val h_prize_date =  view.findViewById<TextView>(R.id.h_prize_date)

        val itemPrize = prizeList[position]
        h_prize_title.text = itemPrize.prize_title
        h_prize_date.text = itemPrize.prize_date


        return view
    }

    override fun getItem(position: Int): Any {
        return prizeList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getCount(): Int {
        return prizeList.size
    }
}