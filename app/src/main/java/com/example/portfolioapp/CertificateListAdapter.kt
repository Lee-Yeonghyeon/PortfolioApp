package com.example.portfolioapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CertificateListAdapter(val context: Context, val certList: ArrayList<ItemCertificate>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_homecert, null)

        val h_cert_title =  view.findViewById<TextView>(R.id.h_cert_title)
        val h_cert_date =  view.findViewById<TextView>(R.id.h_cert_date)

        val itemPrize = certList[position]
        h_cert_title.text = itemPrize.cert_title
        h_cert_date.text = itemPrize.cert_date


        return view
    }

    override fun getItem(position: Int): Any {
        return certList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getCount(): Int {
        return certList.size
    }
}