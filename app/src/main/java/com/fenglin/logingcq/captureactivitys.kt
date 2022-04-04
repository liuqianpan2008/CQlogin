package com.fenglin.logingcq

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.fenglin.logingcq.data.Res
import com.google.gson.Gson
import com.google.zxing.Result
import com.king.zxing.CaptureActivity
import org.json.JSONObject

class captureactivitys : CaptureActivity() {
    override fun onScanResultCallback(result: Result): Boolean {
        Log.i("扫码结果", result.text)
        val req= Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,"http://10.52.43.103:8080/users/PhoneVerification",
            JSONObject(Gson().toJson(mapOf("cod" to result.text))),{
                val res =Gson().fromJson(it.toString(), Res::class.java)
                if (res.date==true){
                    Toast.makeText(this,res.msg, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity3::class.java).putExtra("cod",result.text))
                }else Toast.makeText(this,res.msg, Toast.LENGTH_SHORT).show()

            },{
                Log.e("loginError", "onCreate: ${it.message}")
            });
        req.add(jsonObjectRequest)
        return super.onScanResultCallback(result)
    }
}