package com.fenglin.login.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HelloWorld {
    @RequestMapping("/")
    fun home() : String{
        return "你好,世界!"
    }
}