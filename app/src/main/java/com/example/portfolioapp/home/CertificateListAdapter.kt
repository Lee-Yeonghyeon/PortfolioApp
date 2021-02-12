package com.example.portfolioapp.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.portfolioapp.R

/*
홈화면 - 자격증
리스트뷰에 넣어 줄 요소를 View와 연결해주는 어댑터를 생성해주는 클래스
BaseAdapte를 상속받음
 */
class CertificateListAdapter(val context: Context, val certList: ArrayList<ItemCertificate>): BaseAdapter() {

    // xml 파일의 ListView와 데이터를 연결해주는 BaseAdapter의 필수 메소드
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_homecert, null)

        val h_cert_title =  view.findViewById<TextView>(R.id.h_cert_title)
        val h_cert_date =  view.findViewById<TextView>(R.id.h_cert_date)

        val itemPrize = certList[position]
        h_cert_title.text = itemPrize.cert_title
        h_cert_date.text = itemPrize.cert_date


        return view
    }

    // 해당 위치의 item을 선택하는 BaseAdapter의 필수 메소드
    override fun getItem(position: Int): Any {
        return certList[position]
    }

    // 해당 위치의 item id를 반환하는 BaseAdapter의 필수 메소드
    override fun getItemId(position: Int): Long {
        return 0    // 실질적으로 id가 필요하지 않아 0을 반환하도록 설정
    }

    // ListView에 속한 item의 수를 반환하는 BaseAdapter의 필수 메소드
    override fun getCount(): Int {
        return certList.size
    }
}