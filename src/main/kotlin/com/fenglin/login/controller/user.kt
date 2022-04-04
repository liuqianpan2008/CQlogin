package com.fenglin.login.controller

import com.fenglin.login.Utils.Res
import com.fenglin.login.service.impl.UserServiceImpl
import lombok.Builder.Default
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.TimeUnit


@RestController
@CrossOrigin("*")
@RequestMapping("/users")
class user(
    @Autowired
    internal val userService: UserServiceImpl,
    @Autowired
    internal val RedisTemplate: StringRedisTemplate,
//    @Autowired
//    internal val userDao: UserDao
) {
//  普通登录
    @PostMapping("/login")
    @CrossOrigin("*")
    fun Login(@RequestBody data: Map<String,String>): Res {
        val userName=data.get("userName")?:"";
        val userPassword=data.get("userPassword")?:"";
        //后端验证
        if (userName.length !in (3..5) || userPassword.length !in (3..10)){
            val r= mapOf("cod" to  false)
            return Res(msg = "输入大小错误！", date = r)
        }
        val id = userService.login(userName,userPassword);
        return if (id>0) {
            val r= mapOf("cod" to  true, "id" to id)
            Res(msg = "登录成功", date = r)
        } else {
            val r= mapOf("cod" to false)
            Res(msg = "登录失败,账号或者密码有误", date = r)
        }
    }
    @CrossOrigin("*")
    @GetMapping("/logincod")
    fun logincod():Res {
        //生成uuid
        val uuid = UUID.randomUUID().toString().replace("-".toRegex(), "")//生成UUID
        RedisTemplate.opsForValue().set(uuid, "-1",60*5, TimeUnit.SECONDS); //存入缓存
        return Res(date = uuid, msg = "生成成功！")
    }
//  网页心跳验证！
    @CrossOrigin("*")
    @PostMapping("/verificationCod")
    fun verificationCod(@RequestBody data: Map<String,String>): Res {
        data["cod"]?.let {
            if (!RedisTemplate.hasKey(it)){
               return Res(date = mapOf("cod" to false), msg = "二维码过期了")
            }
        }
        val cod = data["cod"]?.let { RedisTemplate.opsForValue().get(it) }
        cod?.let {
            when(it){
                "-1" -> { return Res(date = mapOf("cod" to false), msg = "扫码等待中！")}
                "0"  -> { return Res(date = mapOf("cod" to false), msg = "已扫码")}
                else -> { return Res(date = mapOf("cod" to true , "data" to cod ), msg = "已登录")}
            }
        }
        return Res(flag = false, date=false, msg = "内部错误")
    }
//  手机扫码校验
    @CrossOrigin("*")
    @PostMapping("/PhoneVerification")
    fun PhoneVerification(@RequestBody data: Map<String,String>): Res {
        data["cod"]?.let {
            if (!RedisTemplate.hasKey(it)){
                return Res(date = false, msg = "二维码过期了")
            }
        }
        val cod = data["cod"]?.let { RedisTemplate.opsForValue().get(it) }
        cod?.let {
            data["cod"]?.let { it1 -> RedisTemplate.opsForValue().set(it1,"0") }
            return Res(date = true, msg = "手机端已扫码")
        }
        return Res(flag = false, date=false, msg = "内部错误")
    }
//  手机端登录
    @CrossOrigin("*")
    @PostMapping("/PhoneLoginCod")
    fun PhoneLoginCod(@RequestBody data: Map<String,String>): Res {
        data["cod"]?.let {
            if (!RedisTemplate.hasKey(it)){
                return Res(date = mapOf("cod" to false), msg = "二维码过期了")
            }
        }
        val cod = data["cod"]?.let { RedisTemplate.opsForValue().get(it) }
        if (cod=="0"){
            val userName=data.get("userName")?:"";
            val userPassword=data.get("userPassword")?:"";
//          内部登录
            val id = userService.login(userName,userPassword);
            return if (id>0) {
                data["cod"]?.let { RedisTemplate.opsForValue().set(it,id.toString()) }
                Res(msg = "登录成功", date = true)
            } else {
                Res(msg = "登录失败,账号或者密码有误", date = false)
            }
        }
        return Res(flag = false, date=false, msg = "内部错误")
    }
}
