package com.fenglin.logingcq

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.fenglin.logingcq.data.Res
import com.fenglin.logingcq.data.User
import com.google.gson.Gson
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var ed_username: EditText? = null
    var ed_passworld: EditText? = null
    var Bt_login:Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ed_username = findViewById(R.id.Ed_username);
        ed_passworld = findViewById(R.id.Ed_passworld);
        Bt_login = findViewById(R.id.bt_login);
        Bt_login?.setOnClickListener {
            Log.i("loginClick", "onCreate: 点击了登录按钮")
            val req= Volley.newRequestQueue(this)
            val user=User(ed_username?.text.toString(),ed_passworld?.text.toString())
            val jsonObjectRequest = JsonObjectRequest(Method.POST,"http://10.52.43.103:8080/users/login",JSONObject(Gson().toJson(user)),{
                val res =Gson().fromJson(it.toString(), Res::class.java)
                if (res.msg=="登录成功"){

//                  储存本地账号
                    val sp=getSharedPreferences("data", MODE_PRIVATE)
                    val editor=sp.edit();
                    editor.putString("userName",user.userName)
                    editor.putString("userPassword",user.userPassword)
                    editor.apply()
                    editor.clear()
//
                    startActivity(Intent(this@MainActivity, MainActivity2::class.java))
                    Log.d("登录信息", "onCreate:"+"账号登录成功！")
                }else{
                    Toast.makeText(this,res.msg,Toast.LENGTH_SHORT).show()
                }
            },{
                Log.e("loginError", "onCreate: ${it.message}")
            })
            req.add(jsonObjectRequest);

        }
    }
}
