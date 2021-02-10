package com.example.mypart_pofo

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.net.URL

class recycle_Adapter(val context: Context, val actList: ArrayList<Act>, val itemClick: (Act) -> Unit) :
    RecyclerView.Adapter<recycle_Adapter.Holder>(){

    //lateinit var sqlitedb: SQLiteDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return actList.size
    }


    inner class Holder(itemView: View? , itemClick : (Act) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        val PhotoImg = itemView?.findViewById<ImageView>(R.id.PhotoImg)
        val actName = itemView?.findViewById<TextView>(R.id.actNametv)
        val actStartDate = itemView?.findViewById<TextView>(R.id.actStartDatetv)
        val actEndDate = itemView?.findViewById<TextView>(R.id.actEndDatetv)

      /*  var cursor: Cursor = sqlitedb.rawQuery("SELECT portfolio WHERE image;", null)
        val uri : String = cursor.getString(5)*/


        fun bind (act: Act, context: Context) {
            /* PhotoImg setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고,
            이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다.*/
            if (act.photo != "") {
                val resourceId = context.resources.getIdentifier(act.photo, "drawable", context.packageName)
                //PhotoImg?.setImageResource(uri) //뭐야...이미지 uri어떻게..

            /*
                var image_task: URLtoBitmapTask = URLtoBitmapTask()
                image_task = URLtoBitmapTask().apply { url = URL("{이미지의 url}") }
                var bitmap: Bitmap = image_task.execute().get()
                PhotoImg?.setImageBitmap(bitmap)*/



            } else {
                PhotoImg?.setImageResource(R.mipmap.ic_launcher)
            }
            /* 나머지 TextView와 String 데이터를 연결한다. */
            actName?.text = act.name
            actStartDate?.text = act.startDate
            actEndDate ?.text = act.EndDate

            itemView.setOnClickListener { itemClick(act) }
            /* (3) itemView가 클릭됐을 때 처리할 일을 itemClick으로 설정한다.
             (Act) -> Unit 에 대한 함수는 나중에 PotfolioFullView.kt 클래스에서 작성한다. */
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(actList[position], context)
    }


}