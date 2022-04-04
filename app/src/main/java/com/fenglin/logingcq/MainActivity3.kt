package com.fenglin.logingcq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.fenglin.logingcq.data.Res
import com.google.gson.Gson
import org.json.JSONObject

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        findViewById<Button>(R.id.queren).setOnClickListener {
            val cod=intent.getStringExtra("cod")
            val sp=getSharedPreferences("data", MODE_PRIVATE)
            val req= Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST,"http://10.52.43.103:8080/users/PhoneLoginCod",
                JSONObject(Gson().toJson(mapOf("cod" to cod,"userName" to sp.getString("userName",""),"userPassword" to sp.getString("userPassword","")))),{
                    val res = Gson().fromJson(it.toString(), Res::class.java)
                    if (res.date==true){
                        Toast.makeText(this,res.msg, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity2::class.java))
                    }else Toast.makeText(this,res.msg, Toast.LENGTH_SHORT).show()
                },{
                    Log.e("loginError", "onCreate: ${it.message}")
                });
            req.add(jsonObjectRequest)
        }
        findViewById<Button>(R.id.quxiao).setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }
    }
}