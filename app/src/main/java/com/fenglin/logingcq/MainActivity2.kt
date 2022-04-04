package com.fenglin.logingcq

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.king.zxing.CaptureActivity


class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        findViewById<Button>(R.id.bt_sten).setOnClickListener {
            startActivity(Intent(this, captureactivitys::class.java))
        }


    }





}