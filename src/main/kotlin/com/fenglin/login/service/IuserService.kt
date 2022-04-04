package com.fenglin.login.service

import com.baomidou.mybatisplus.extension.service.IService
import com.fenglin.login.domain.User

interface  IuserService : IService<User> {
    fun login( userName : String, userpasswd : String ):Int
}