package com.example.portfolioapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class PortListAdapter(val context: Context, val portList: ArrayList<ItemPort>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_homeport, null)

        val h_port_title =  view.findViewById<TextView>(R.id.h_port_title)
        val h_port_startdate =  view.findViewById<TextView>(R.id.h_port_startdate)
        val h_port_enddate =  view.findViewById<TextView>(R.id.h_port_enddate)
        val h_port_content =  view.findViewById<TextView>(R.id.h_port_content)

        val itemPort = portList[position]
        h_port_title.text = itemPort.port_title
        h_port_startdate.text = itemPort.port_startdate
        h_port_enddate.text = itemPort.port_enddate
        h_port_content.text = itemPort.port_content

        return view
    }

    override fun getItem(position: Int): Any {
        return portList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getCount(): Int {
        return 5
    }
}