package com.example.portfolioapp.portFullView
/*리사이클뷰 어댑터 선언*/
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfolioapp.R

class recycle_Adapter(val context: Context, val actList: ArrayList<Act>,val itemClick : (Act) -> Unit) :
    RecyclerView.Adapter<recycle_Adapter.Holder>(){

    //ViewHolder클래스 선언
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)
        //화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성
        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return actList.size   //RecyclerView로 만들어지는 item의 총 개수를 반환
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(actList[position], context)  //onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결
    }


    inner class Holder(itemView: View?, itemClick: (Act) -> Unit ) : RecyclerView.ViewHolder(itemView!!) {

        val actName = itemView?.findViewById<TextView>(R.id.actNametv)
        val actSort = itemView?.findViewById<TextView>(R.id.actSort)
        val actStartDate = itemView?.findViewById<TextView>(R.id.actStartDatetv)
        val actEndDate = itemView?.findViewById<TextView>(R.id.actEndDatetv)


        fun bind (act: Act, context: Context) {

            /* 나머지 TextView와 String 데이터를 연결한다. */
            actName?.text = act.name
            actSort?.text = act.sort
            actStartDate?.text = act.startDate
            actEndDate ?.text = act.EndDate

           itemView.setOnClickListener { itemClick(act) }
            /* itemView가 클릭됐을 때 처리할 일을 itemClick으로 설정한다.
             (Act) -> Unit 에 대한 함수는 나중에 PotfolioFullView.kt 클래스에서 작성한다. */
        }

    }

}