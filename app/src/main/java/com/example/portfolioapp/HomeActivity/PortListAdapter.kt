package com.example.portfolioapp.HomeActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.portfolioapp.R

class PortListAdapter(val context: Context, val portList: ArrayList<ItemPort>): BaseAdapter() {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view3: View = LayoutInflater.from(context).inflate(R.layout.item_homeport, null)

        val h_port_title =  view3.findViewById<TextView>(R.id.h_port_title)
        val h_port_date =  view3.findViewById<TextView>(R.id.h_port_date)
        val h_port_content =  view3.findViewById<TextView>(R.id.h_port_content)

        val itemPort = portList[position]
        h_port_title.text = itemPort.port_title
        h_port_date.text = itemPort.port_date
        h_port_content.text = itemPort.port_content

        return view3
    }

    override fun getItem(position: Int): Any {
        return portList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getCount(): Int {
        return portList.size
    }
}