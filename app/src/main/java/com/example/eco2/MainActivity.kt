package com.example.eco2

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    
    lateinit var todoBtn: ImageButton
    lateinit var newsBtn: ImageButton

    lateinit var add_to_do:TextView
    var todoList:ArrayList<String> = arrayListOf()

    var countDownTimer: CountDownTimer? = null
    var tv_timer: TextView? = null

    //탄소 배출 감소량 계산
    lateinit var myCarbon_kg: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        todoBtn = findViewById(R.id.todoButton)
        newsBtn = findViewById(R.id.newsButton)

        tv_timer = findViewById(R.id.tv_timer)

        add_to_do = findViewById(R.id.add_to_do)

        myCarbon_kg = findViewById(R.id.myCarbon_kg)
        
        todoBtn.setOnClickListener{
            var intent = Intent(this, todoActivity::class.java)
            startActivity(intent)
        }

        newsBtn.setOnClickListener{
            var intent = Intent(this, newsActivity::class.java)
            startActivity(intent)
        }

        countDownTimer = object : CountDownTimer(200000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timer=tv_timer
                timer?.setText(getTime())
            }

            override fun onFinish() {}
        }
        val count=countDownTimer
        count?.start()

        //결과 값 받기
        val result = intent.getFloatExtra("RESULT", 0.00f)
        myCarbon_kg.setText("${String.format("%.2f", result)}kg/co2")


        todoList.add("개인 컵 사용하기  |  0.29kg")
        todoList.add("에코백 사용하기  |  0.31kg")
        todoList.add("물 절약하기  |  0.24k")
        todoList.add("계단 이용하기  |  0.46kg")
        todoList.add("가까운 거리는 도보 이용하기  |  0.26kg")
        todoList.add("로컬푸드 구매하기  |  0.13kg")
        todoList.add("불필요한 메일 10통 삭제  |  0.04kg")
        val random = Random()
        val randomNum = random.nextInt(6)
        add_to_do.text =todoList[randomNum]
    }



    private fun getTime(): String? {
        val date = Date()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        val c_year = calendar[Calendar.YEAR]
        val c_month = calendar[Calendar.MONTH]
        val c_day = calendar[Calendar.DAY_OF_MONTH]
        val c_hour = calendar[Calendar.HOUR_OF_DAY]
        val c_min = calendar[Calendar.MINUTE]
        val c_sec = calendar[Calendar.SECOND]
        val baseCal: Calendar = GregorianCalendar(c_year, c_month, c_day, c_hour, c_min, c_sec)
        val targetCal: Calendar = GregorianCalendar(2029, 6, 24, 12, 36, 56) //비교대상날짜
        val diffSec = (targetCal.timeInMillis - baseCal.timeInMillis) / 1000
        val diffDays = diffSec / (24 * 60 * 60)
        targetCal.add(Calendar.DAY_OF_MONTH, (-diffDays).toInt())
        val dayTime = Math.floor((diffDays).toDouble()).toInt()
        val hourTime = Math.floor((diffSec / 3600-(dayTime*24)).toDouble()).toInt()
        val minTime = Math.floor(((diffSec - (3600 * 24 *dayTime)-(3600*hourTime)) / 60).toDouble()).toInt()
        val secTime = Math.floor(((diffSec - (3600 * 24 *dayTime)-(3600*hourTime)) % 60).toDouble()).toInt()
        val day = String.format("%02d", dayTime)
        val hour = String.format("%02d", hourTime)
        val min = String.format("%02d", minTime)
        val sec = String.format("%02d", secTime)
        return "지구 온도가 1.5℃ 오르기까지\n" + day + "일 " +  hour + " : " + min + " : " + sec
    }

}