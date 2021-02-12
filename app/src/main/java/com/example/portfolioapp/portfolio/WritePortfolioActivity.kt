package com.example.portfolioapp.portfolio
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.portfolioapp.R
import com.example.portfolioapp.certprizeFullView.CertificateViewActivity
import com.example.portfolioapp.home.HomeActivity
import com.example.portfolioapp.portFullView.PortfolioFullViewActivity
import java.util.*

class WritePortfolioActivity : AppCompatActivity() {

    //변수 선언
    lateinit var edt_writeP_name: EditText
    lateinit var btn_writeP_selectDateStart: Button
    lateinit var calendarTextViewStart: TextView
    lateinit var btn_writeP_selectDateEnd: Button
    lateinit var calendarTextViewEnd: TextView
    lateinit var edt_writeP_content: EditText
    lateinit var btn_writeP_picture: Button
    lateinit var btn_writeP_complete: Button
    lateinit var imageView : ImageView
    lateinit var imageUri : String
    lateinit var edt_writeP_url : EditText

    //네비게이션바
    lateinit var nav_portfolio: ImageView
    lateinit var nav_home: ImageView
    lateinit var nav_certificate: ImageView


    //DB관련
    lateinit var portfolio: PorflioManager
    lateinit var sqlitedb: SQLiteDatabase

    //갤러리 관련
    private val REQUEST_READ_EXTERNAL_STORAGE = 1000
    private val OPEN_GALLERY = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_portfolio)

        supportActionBar?.setTitle("활동 입력하기")

        //변수와 위젯 연결
        edt_writeP_name = findViewById(R.id.edt_writeP_name)
        btn_writeP_selectDateStart = findViewById(R.id.btn_writeP_selectDateStart)
        calendarTextViewStart = findViewById(R.id.calendarTextViewStart)
        btn_writeP_selectDateEnd = findViewById(R.id.btn_writeP_selectDateEnd)
        calendarTextViewEnd = findViewById(R.id.calendarTextViewEnd)
        edt_writeP_content = findViewById(R.id.edt_writeP_content)
        btn_writeP_picture = findViewById(R.id.btn_writeP_picture)
        btn_writeP_complete = findViewById(R.id.btn_writeP_complete)
        imageView = findViewById(R.id.imageView)
        edt_writeP_url = findViewById(R.id.edt_writeP_url)

        nav_portfolio = findViewById(R.id.nav_portfolio)
        nav_home = findViewById(R.id.nav_home)
        nav_certificate = findViewById(R.id.nav_certificate)

        // 아래 버튼 3개
        nav_portfolio.setOnClickListener {
            val intent = Intent(this, PortfolioFullViewActivity::class.java)
            startActivity(intent)
        }
        nav_home.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        nav_certificate.setOnClickListener {
            val intent = Intent(this, CertificateViewActivity::class.java)
            startActivity(intent)
        }


        btn_writeP_picture.setOnClickListener { openGallery() }  //사진 첨부를 눌렀을때 갤러리에서 사진을 가져옵니다


        //spinne관련
        val spinner_writeP_sort: Spinner = findViewById(R.id.spinner_writeP_sort)

        var sData = resources.getStringArray(R.array.list_array)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sData)
        spinner_writeP_sort.adapter = adapter

        spinner_writeP_sort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> "전체"
                    1 -> "대외활동"
                    2 -> "공모전"
                    3 -> "동아리"
                    4 -> "교내활동"
                    5 -> "기타"
                    else -> null
                }


                Toast.makeText(this@WritePortfolioActivity, adapter.getItem(position) + "선택했습니다", Toast.LENGTH_SHORT).show()
                //선택한 항목 토스트 메세지 출력
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("활동 종류를 선택하세요")
            }

        }

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)


        //DB연동
        portfolio = PorflioManager(this, "portfolio", null, 1)

        //작성완료 버튼을 눌렀을때
        btn_writeP_complete.setOnClickListener {
            var activity_name: String = edt_writeP_name.text.toString()
            var activity_date_start: String = ""
            var activity_date_end: String = ""
            var activity_content: String = edt_writeP_content.text.toString()
            var activity_url : String = edt_writeP_url.text.toString()

            if (calendarTextViewStart.text !== null) {
                activity_date_start = calendarTextViewStart.text.toString()
            }

            if (calendarTextViewEnd.text !== null) {
                activity_date_end = calendarTextViewEnd.text.toString()
            }

            var spinnerString = spinner_writeP_sort.selectedItem
            sqlitedb = portfolio.writableDatabase
            sqlitedb.execSQL(
                    "INSERT INTO portfolio VALUES ('" + activity_name + "','"
                            + activity_date_start + "','" + activity_date_end + "','" + spinnerString + "','" + activity_content + "', '" + imageUri +"','" + activity_url+ "');")
            Log.d("myDB", "spinner: " + spinnerString)
            sqlitedb.close()

            Log.d("myDB", "activiy_name_write: " + activity_name)
            //작성완료 버튼 누르면 -> 포트폴리오 월별 보기로 넘어감
            val intent = Intent(this, PortfolioFullViewActivity::class.java)
            intent.putExtra("intent_name", activity_name)
            startActivity(intent)

            //작성완료 토스트 메세지
            Toast.makeText(this, "작성 완료", Toast.LENGTH_SHORT).show()

        }


        //날짜선택 시 캘린더 나오기(시작날짜)
        btn_writeP_selectDateStart.setOnClickListener {
            var today = Calendar.getInstance()
            var year: Int = today.get(Calendar.YEAR)
            var month: Int = today.get(Calendar.MONTH)
            var date: Int = today.get(Calendar.DATE)

            var listener =
                    DatePickerDialog.OnDateSetListener { view: DatePicker?, year, month, date ->
                        calendarTextViewStart.text = "${year}년 ${month + 1}월 ${date}일"
                    }
            var picker = DatePickerDialog(this, listener, year, month, date)
            picker.show()
        }

        //날짜선택 시 캘린더 나오기(종료날짜)
        btn_writeP_selectDateEnd.setOnClickListener {
            var today = Calendar.getInstance()
            var year: Int = today.get(Calendar.YEAR)
            var month: Int = today.get(Calendar.MONTH)
            var date: Int = today.get(Calendar.DATE)

            var listener =
                    DatePickerDialog.OnDateSetListener { view: DatePicker?, year, month, date ->
                        calendarTextViewEnd.text = "${year}년 ${month + 1}월 ${date}일"
                    }
            var picker = DatePickerDialog(this, listener, year, month, date)
            picker.show()
        }

            //권한이 부여되었는지 확인
            if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
            )

            //권한이 허용되지 않음
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                ) {
                    //이전에 거부한 적이 있으면
                    var dlg = AlertDialog.Builder(this)
                    dlg.setTitle("사진 권한")
                    dlg.setMessage("사진 정보를 얻기 위해서는 외부 저장소 권한이 필수적으로 필요합니다")
                    dlg.setPositiveButton("확인") { dialog, which ->
                        ActivityCompat.requestPermissions(
                                this@WritePortfolioActivity
                                ,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                REQUEST_READ_EXTERNAL_STORAGE
                        )
                        dlg.setNegativeButton("취소", null)
                        dlg.show()
                    }
                } else {
                    //처음 권한 요청하는 경우
                    ActivityCompat.requestPermissions(
                            this@WritePortfolioActivity
                            ,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_READ_EXTERNAL_STORAGE
                    )
                } else {
                //권한이 이미 허용된 경우

            }

        }

    //뒤로가기 버튼

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                val intent = Intent(this, PortfolioFullViewActivity::class.java)
                startActivity(intent)
                return true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }
    }


    //image_url 절대경로 변환
    fun absolutelyPath(path: Uri): String {
        var result:String
        var cursor:Cursor = getContentResolver().query(path, null, null, null, null)!!

        if (cursor == null) { // Source is Dropbox or other similar local file path

            result = path.getPath()!!



        } else {

            cursor.moveToFirst()

            var idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)

            result = cursor.getString(idx)

            cursor.close()

        }
        result = path.getPath()!!
        result="com.android.providers.media.documents"+result
        Log.d("myDB","result: "+result)
        return result


    }

    private fun openGallery() {

        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    //image_url 절대경로
    fun getFullPathFromUri(fileUri: Uri?): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor: Cursor = getContentResolver().query(fileUri!!, null, null, null, null)!!
        if (cursor != null) {
            cursor.moveToFirst()
            var document_id = cursor.getString(0)
            if (document_id == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + " = ? ",
                        arrayOf(document_id),
                        null
                    )!!
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    cursor?.close()
                }
            }
        }
        return fullPath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {
                var dataUri = data?.dataString
                Log.d("myDB", "url: " + dataUri)
                var currentImageUrl: Uri? = data?.data

                //imageUri = absolutelyPath(currentImageUrl!!)  //사진 uri정보를 imageUri가 가지고 있음
                imageUri = getFullPathFromUri(currentImageUrl!!)!!
                Log.d("myDB", "aburl: " + dataUri)

                try {
                    var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    imageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            Log.d("ActivityResult", "something wrong")
        }

    }



}


   




